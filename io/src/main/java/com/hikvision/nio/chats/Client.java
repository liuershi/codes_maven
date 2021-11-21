package com.hikvision.nio.chats;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author zhangwei151
 * @date 2021/11/14 16:33
 */
public class Client {
    public static void main(String[] args) throws IOException {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(9999));
            System.out.println("客户端：" + channel.getLocalAddress().toString() + " 连接成功");
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            // 启动现场接受其他客户的消息
            new Thread(() -> {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                while (true) {
                    try {
                        selector.select();
                        readBuffer.clear();
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()){
                            System.out.println();
                            SelectionKey key = iterator.next();
                            SocketChannel c = (SocketChannel) key.channel();
                            int length = c.read(readBuffer);
                            System.out.println("接收到的消息为：" + new String(readBuffer.array(), 0, length));
                            buffer.clear();
                            iterator.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            while (true) {
                System.out.println("请说：");
                String content = scanner.nextLine();
                buffer.put(content.getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
