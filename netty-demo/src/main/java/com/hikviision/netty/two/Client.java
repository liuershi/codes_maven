package com.hikviision.netty.two;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author milindeyu
 * @Date 2021/11/17 5:54 下午
 * @Version 1.0
 */
@Slf4j
public class Client {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder());

                            // 对于一些耗时操作，尽量不要在NioEventLoop中执行，避免此线程上的其他Chanel被阻塞
                            // 推荐使用EventLoopGroup的另一个实现DefaultEventLoopGroup
                            EventLoopGroup businessGroup = new DefaultEventLoopGroup();
                            pipeline.addLast(businessGroup, "sleepHandler", new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("current thread name : {}",  Thread.currentThread().getName());
                                    TimeUnit.SECONDS.sleep(2);
                                    super.channelActive(ctx);
                                }
                            });


                            pipeline.addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("current thread name : {}",  Thread.currentThread().getName());
                                    ctx.writeAndFlush("hello world");
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9999)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
