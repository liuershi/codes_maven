package com.hikvision.producer.producer;

import com.hikvision.model.entity.BrokerMessageLog;
import com.hikvision.model.entity.Order;
import com.hikvision.model.utils.JsonUtil;
import com.hikvision.producer.dao.BrokerMessageLogDOMapper;
import com.hikvision.producer.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zhangwei151
 * @date 2021/12/3 14:14
 */
@Component
@Slf4j
public class RabbitmqOrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogDOMapper logDOMapper;

    /**
     * 回调函数，确认消息发送到broker
      */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.error("receive correlationData is : {}", JsonUtil.toJson(cause));
            String id = correlationData.getId();
            if (ack) {
                // 如果confirm返回成功 则进行更新
                BrokerMessageLog messageLog = new BrokerMessageLog();
                messageLog.setMessageId(id);
                messageLog.setStatus(StatusEnum.ORDER_SEND_SUCCESS.getCode());
                messageLog.setUpdateTime(LocalDateTime.now());
                logDOMapper.updateById(messageLog);
                return;
            }
            // 失败则进行具体的后续操作:重试 或者补偿等手段
            log.info("失败，进行后续操作...");
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     */
    public void sendOrder(Order order) {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
    }
}
