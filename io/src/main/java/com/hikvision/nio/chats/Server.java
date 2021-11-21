package com.hikvision.nio.chats;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 群聊实现的服务端
 *
 * @author zhangwei151
 * @date 2021/11/14 15:40
 */
public class Server {
    /**
     * 当前在线的客户端连接
     */
    private static final Map<String, SocketChannel> USERS = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // 1.设置成服务端通道为非阻塞模式
            ssc.configureBlocking(false);
            // 2.监听指定端口
            ssc.bind(new InetSocketAddress(9999));
            // 3.获取多路复用器
            Selector selector = Selector.open();
            // 4.服务端通道注册accept事件到此多路复用器上
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动完成...");
            System.out.println("开始监听客户的请求~~~");
            while (true) {
                // 5.阻塞，有事件时触发后续处理
                selector.select();
                // 6.获取准备就绪事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // 7.连接事件时将socketChannel也注册到多路复用器上
                        ServerSocketChannel sChannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = sChannel.accept();
                        // 也设置为非阻塞
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        // 加入在线集合
                        String remote = channel.getRemoteAddress().toString();
                        System.out.println("客户端："+ remote+ " 上线啦~~~");
                        USERS.put(remote.split(":")[1], channel);
                        System.out.println("当前在线客户端为：" + USERS.values().toString());
                    } else if (key.isReadable()) {
                        // 8.读事件时将消息转发到其他用户
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        buffer.clear();
                        int length;
                        try {
                            while ((length = channel.read(buffer)) > 0) {
                                String content = new String(buffer.array(), 0, length);
                                System.out.println("服务器收到消息：" + content);
                                if (content.contains(":")) {
                                    // 此时为私聊
                                    String[] split = content.split(":");
                                    SocketChannel target = USERS.get(split[0]);
                                    if (target != null) {
                                        target.write(ByteBuffer.wrap(split[1].getBytes(StandardCharsets.UTF_8)));
                                    }
                                } else {
                                    // 否则为群聊
                                    USERS.values().forEach(user -> {
                                        if (user != channel) {
                                            try {
                                                user.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8)));
                                            } catch (IOException e) {
                                                // 客户端离线
                                                try {
                                                    USERS.remove(user.getRemoteAddress().toString().split(":")[1]);
                                                } catch (IOException ioException) {
                                                    ioException.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {
                            String remote = channel.getRemoteAddress().toString();
                            System.out.println("客户端(" + remote + ")离线了！！！" );
                            // 客户端离线
                            key.cancel();
                            USERS.remove(remote.split(":")[1]);
                            System.out.println("当前在线客户端为：" + USERS.values().toString());
                        }
                    }
                    // 先移除，否则下次事件进来会存在旧的事件
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
