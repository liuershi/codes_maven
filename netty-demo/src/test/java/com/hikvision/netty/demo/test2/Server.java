package com.hikvision.netty.demo.test2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * @author zhangwei151
 * @description Server
 * @date 2021/9/13 22:11
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(9999));

        while (true) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);

                    // 向客户端写大量数据
                    StringBuilder sb = new StringBuilder();
                    IntStream.range(0, 8000000).forEach(i -> sb.append("b"));
                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(sb.toString());

                    // todo: 注意，此时使用死循环不断写时，由于底层SOCKET缓冲区有大小限制，所以可能存在
                    // 写的过程中缓冲区满了，此时不断写实际没写进去，这样导致很多次无效的写
                    /*while (byteBuffer.hasRemaining()) {
                        // 返回实际写的字节数
                        int write = sc.write(byteBuffer);
                        System.out.println(write);
                    }*/

                    // todo:解决，同个
                    int write = sc.write(byteBuffer);
                    System.out.println(write);
                    if (byteBuffer.hasRemaining()) {
                        // 注册写事件，注意不要直接覆盖，可能之前存在别的时间，否则之前的时间就会被覆盖，例如此例中scKey还注册了读事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        scKey.attach(byteBuffer);
                    }
                } else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    int write = channel.write(buffer);
                    System.out.println(write);
                    if (!buffer.hasRemaining()) {
                        key.interestOps(key.interestOps());
                        key.attach(null);
                    }
                }
            }
        }
    }
}
