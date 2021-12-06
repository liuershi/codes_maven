package com.hikvision.producer.producer;

import com.hikvision.model.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei151
 * @date 2021/12/3 14:11
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Order order) {
        CorrelationData data = new CorrelationData();
        data.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.abcd", order, data);

        /**
         * 还要在 rabbitmq 控制台配置exchange和queue，并绑定
         * 加绑定在控制台的exchange和queues哪一块都可以
         */
    }
}
