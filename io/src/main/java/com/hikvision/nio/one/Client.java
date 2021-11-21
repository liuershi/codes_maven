package com.hikvision.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author zhangwei151
 * @date 2021/11/14 15:11
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 9999));

        Scanner scanner = new Scanner(System.in);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            System.out.print("请说：");
            String content = scanner.nextLine();
            buffer.put(content.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            sc.write(buffer);
        }
    }
}
