package com.hikvision.rabbitmq.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.CredentialsProvider;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 学习rabbitmq官网第一个例子
 * {@see https://www.rabbitmq.com/tutorials/tutorial-one-java.html}
 *
 * @author zhangwei151
 * @date 2021/12/2 14:25
 */
public class Send {

    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "123456";

    public static final CredentialsProvider CRED =  new DefaultCredentialsProvider(USER_NAME, USER_PASSWORD);

    protected static final String QUEUE_NAME = "hikvision.hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 指定当前的rabbitmq地址
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(CRED);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            final AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = "hello world!";
            // 使用默认交换机
            channel.basicPublish("", QUEUE_NAME, false, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
