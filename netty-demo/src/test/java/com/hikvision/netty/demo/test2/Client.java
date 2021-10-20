package com.hikvision.netty.demo.test2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zhangwei151
 * @description Client
 * @date 2021/9/13 22:20
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(9999));

        int sum = 0;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        while (true) {
            int read = sc.read(byteBuffer);
            sum += read;
            System.out.println(sum);
            byteBuffer.clear();
        }
    }
}
