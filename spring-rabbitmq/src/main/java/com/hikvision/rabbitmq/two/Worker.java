package com.hikvision.rabbitmq.two;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangwei151
 * @date 2021/12/2 16:03
 */
public class Worker {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 先声明队列
        channel.queueDeclare(NewTask.TASK_QUEUE_NAME, true, false, false, null);
        // 每次能处理请求数，设置为1保证多个请求可以分发给不同的worker
        channel.basicQos(1);

        channel.basicConsume(NewTask.TASK_QUEUE_NAME, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {});
    }

    private static void doWork(String message) {
        for (char c : message.toCharArray()) {
            if (' ' == c) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
