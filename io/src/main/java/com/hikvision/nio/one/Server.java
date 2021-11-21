package com.hikvision.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 简单的nio服务端
 *
 * @author zhangwei151
 * @date 2021/11/13 23:16
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置为非阻塞模式
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));

        // 获取选择器
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start done");
        while (true) {
            // 该方法无可用事件时会阻塞
            selector.select();
            // 拿到当前事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 为连接事件时，获取对应的SocketChannel对象
                    SocketChannel sc = ssc.accept();
                    System.out.println("接入新的连接：" + sc.getRemoteAddress().toString());
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length;
                    try {
                        while ((length = channel.read(buffer)) > 0) {
                            buffer.clear();
                            System.out.println("客户端(" + channel.getRemoteAddress().toString() + ") 说："  + new String(buffer.array(), 0, length));
                        }
                    } catch (IOException e) {
                        // 客户端离线
                        System.out.println("客户端：" + channel.getRemoteAddress() + "离线了~~~");
                        key.cancel();
                    }
                }
                iterator.remove();
            }
        }
    }
}
