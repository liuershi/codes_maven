package com.hikvision.rabbitmq.five;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * topic交换机的生产者
 * {@see https://www.rabbitmq.com/tutorials/tutorial-five-java.html}
 *
 * @author zhangwei151
 * @date 2021/12/2 19:14
 */
public class EmitLogTopic {

    protected static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = "lazy1.orange.rabbit1";
            String message = "HELLO, I AM TOPIC";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
}
