package com.hikvision.rabbitmq.four;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangwei151
 * @date 2021/12/2 17:30
 */
public class ReceiveLogsDirect {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EmitLogDirect.EXCHANGE_NAME, "direct");

        String queue = "queue2";
        channel.queueDeclare(queue, false, true, true, null);

        channel.queueBind(queue, EmitLogDirect.EXCHANGE_NAME, "debug");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queue, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        }, consumerTag -> {});
    }
}
