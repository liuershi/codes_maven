package com.hikvision.consumer.handler;

import com.hikvision.model.entity.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangwei151
 * @date 2021/12/3 15:31
 */
@Component
@Slf4j
public class OrderReceive {

    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(value = "order-exchange", durable = "true", type = "topic"),
            key = "order.*"
    ))
    //如果有消息过来，在消费的时候调用这个方法
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Header Map<String, Object> header, Channel channel) throws IOException {
        log.info("---------收到消息，开始消费---------");
        log.info("订单ID:{}", order.getId());
        // 拿到当前消息的推送标识，该值在此channel内是唯一的
        Long tag = (Long) header.get(AmqpHeaders.DELIVERY_TAG);

        boolean multiple = false;
        // multiple属性代表是否一次确认多喝消息，如果true则会将值小于tag的其他消息一并ack
        channel.basicAck(tag, multiple);
    }
}
