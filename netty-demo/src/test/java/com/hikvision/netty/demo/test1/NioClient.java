package com.hikvision.netty.demo.test1;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwei151
 * @description NioClient
 * @date 2021/8/29 22:18
 */
public class NioClient {

    @Test
    public void testClient() throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(9999));
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
