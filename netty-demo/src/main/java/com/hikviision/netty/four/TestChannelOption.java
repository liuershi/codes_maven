package com.hikviision.netty.four;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * 测试通道调优
 *
 * @author zhangwei151
 * @date 2021/12/12 20:19
 */
public class TestChannelOption {
    public static void main(String[] args) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    // ChannelOption.CONNECT_TIMEOUT_MILLIS:指连接超时时间，单位为毫秒
                    // 超时时会抛出一个netty的ConnectTimeoutException连接超时异常
                    // 不过就算该参数指定较大，底层Socket连接失败确定无法连接时还是会快速抛出一个底层连接异常java.net.ConnectException，所以该参数设置较大无太大意义
                    // 底层通过定提交一个定时任务，延迟指定毫秒数，检测连接的promise是否成功，失败则直接抛出ConnectTimeoutException异常
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300)
                    // 该参数设置底层Socket读写操作的超时时间
//                    .option(ChannelOption.SO_TIMEOUT, 1)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 9999)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
