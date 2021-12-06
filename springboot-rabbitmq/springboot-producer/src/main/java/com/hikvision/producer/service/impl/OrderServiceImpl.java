package com.hikvision.producer.service.impl;

import com.hikvision.model.entity.BrokerMessageLog;
import com.hikvision.model.entity.Order;
import com.hikvision.model.utils.JsonUtil;
import com.hikvision.producer.dao.BrokerMessageLogDOMapper;
import com.hikvision.producer.dao.OrderMapper;
import com.hikvision.producer.producer.RabbitmqOrderSender;
import com.hikvision.producer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zhangwei151
 * @date 2021/12/3 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private BrokerMessageLogDOMapper brokerMessageLogDOMapper;

    @Autowired
    private RabbitmqOrderSender sender;

    @Override
    public void createOrder(Order order) {
        LocalDateTime now = LocalDateTime.now();
        // 插入业务数据
        mapper.insert(order);
        // 插入消息记录表数据
        BrokerMessageLog log = BrokerMessageLog.builder()
                // 消息唯一ID
                .messageId(order.getMessageId())
                // 保存消息整体 转为JSON 格式存储入库
                .message(JsonUtil.toJson(order))
                // 设置消息状态为0 表示发送中
                .status("0")
                // 设置消息未确认超时时间窗口为 一分钟
                .nextRetry(now.plusMinutes(1))
                .createTime(now)
                .updateTime(now)
                .build();
        brokerMessageLogDOMapper.insert(log);
        // 发送消息
        sender.sendOrder(order);
    }
}
