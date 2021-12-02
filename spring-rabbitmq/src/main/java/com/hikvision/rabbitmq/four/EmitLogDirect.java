package com.hikvision.rabbitmq.four;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 第四个例子
 * {@see https://www.rabbitmq.com/tutorials/tutorial-four-java.html}
 *
 * @author zhangwei151
 * @date 2021/12/2 17:27
 */
public class EmitLogDirect {

    protected static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 发送消息
            String level = "debug";
            String message = "this is " + level + " info";
            channel.basicPublish(EXCHANGE_NAME, level, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + level + "':'" + message + "'");
        }
    }
}
