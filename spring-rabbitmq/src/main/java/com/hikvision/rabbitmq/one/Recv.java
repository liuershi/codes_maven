package com.hikvision.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangwei151
 * @date 2021/12/2 14:40
 */
public class Recv {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);
        // 这里不需要像生产者一样使用try-with-resource，因为如果这样做了程序会直接关闭资源结束，而我们想要的效果是监听消息
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 这里也需要声明队列，因为可能消费者先于生产者启动，我们需要保证消费者启动时队列存在
        channel.queueDeclare(Send.QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 声明回调监听消息

        channel.basicConsume(Send.QUEUE_NAME, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> {});
    }
}
