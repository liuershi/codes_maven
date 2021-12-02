package com.hikvision.rabbitmq.three;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq官网fanout交换机例子中生产者
 *
 * {@see https://www.rabbitmq.com/tutorials/tutorial-three-java.html}
 *
 * @author zhangwei151
 * @date 2021/12/2 16:55
 */
public class EmitLog {

    protected static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明一个扇形交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "hello, i am fanout";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
