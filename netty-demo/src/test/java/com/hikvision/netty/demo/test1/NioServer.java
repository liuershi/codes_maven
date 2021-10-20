package com.hikvision.netty.demo.test1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangwei151
 * @description 演示JDK的nio使用
 * @date 2021/8/29 22:14
 */
@Slf4j
public class NioServer {

    @Test
    public void testServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 注册该ssc到selector中且只感兴趣accept事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(9999));

        log.info("server start success");
        while (true) {
            // 该方法若selector上未产生事件则阻塞，否则向下执行
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = serverSocketChannel.accept();
                    log.info("current channel is {}", serverSocketChannel);
                    log.info("client {} connection success", sc);
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    try {
                        // 注意：客户端关闭时会触发一个读事件
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int read = socketChannel.read(buffer);
                        // 情形二：客户端正常断开时不会抛出异常，但是此时是通过read()返回值来判断的
                        // 正常情况下返回的是实际读取到的字节数，而断开则返回-1
                        if (read == -1) {
                            key.cancel();
                            return;
                        }
                        log.info("client {} receive message = {}", socketChannel, new String(buffer.array(), 0, read));
                        buffer.clear();
                    } catch (IOException e) {
                        // 情形一：客户端异常断开时会抛出异常
                        key.cancel();
                    }
                }
                // 需手动移除已处理的key
                iterator.remove();
            }
        }

//        old();
    }

    /**
     * 使用死循环不停轮训SocketChannel的方式，缺点是导致CPU空转
     * @throws IOException
     */
    private void old() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置为非阻塞,默认为true
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        ByteBuffer buffer = ByteBuffer.allocate(16);

        List<SocketChannel> channels = new ArrayList<>();

        while (true) {
//            log.info("server start success..");
            SocketChannel sc = ssc.accept();
            // 设置为非阻塞后accept返回的为null
            if (sc != null) {
                log.info("client {} connection success", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel socketChannel : channels) {
                int read = socketChannel.read(buffer);
                // socketChannel设置为非阻塞后若无可读数据则返回0
                if (read > 0) {
                    log.info("client {} receive message = {}", socketChannel, StandardCharsets.UTF_8.decode(buffer).toString());
                }
                buffer.clear();
            }
        }
    }
}
