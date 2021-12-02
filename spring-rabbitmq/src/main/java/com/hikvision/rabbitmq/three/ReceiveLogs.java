package com.hikvision.rabbitmq.three;

import com.hikvision.rabbitmq.one.Send;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 扇形交换机中的消费者
 *
 * @author zhangwei151
 * @date 2021/12/2 17:00
 */
public class ReceiveLogs {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.13");
        factory.setCredentialsProvider(Send.CRED);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明交换机，防止消费者先启动导致找不到交换机
        channel.exchangeDeclare(EmitLog.EXCHANGE_NAME, "fanout");

        // 由于是扇形交换机，需要所有绑定到此交换机的队列是非持久化、独占的、自动删除的
        // channel的无参方法就是声明这样一个队列
        String queue = channel.queueDeclare().getQueue();

        // 将交换机和队列绑定起来，由于是扇形交换机，路由键设置为空字符串，不能为null
        channel.queueBind(queue, EmitLog.EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queue, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> {});
    }
}
