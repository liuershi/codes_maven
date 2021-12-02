package com.hikvision.rabbitmq.five;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangwei151
 * @date 2021/12/2 19:18
 */
public class ReceiveLogsTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EmitLogTopic.EXCHANGE_NAME, "topic");

        String queue = "q2";
        channel.queueDeclare(queue, true, true, true, null);

        // 绑定交换机和队列
         String routingKey = "*.*.rabbit,lazy.#";
//        String routingKey = "*.orange.*";
        for (String key : routingKey.split(",")) {
            channel.queueBind(queue, EmitLogTopic.EXCHANGE_NAME, key);
        }


        channel.basicConsume(queue, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("receive message : " + message);
        }, consumerTag -> {});
    }
}
