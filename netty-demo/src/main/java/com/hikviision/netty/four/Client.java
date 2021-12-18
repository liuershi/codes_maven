package com.hikviision.netty.four;

import com.hikviision.netty.four.handler.BusinessHandler;
import com.hikviision.netty.four.handler.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:01 下午
 * @Version 1.0
 */
@Slf4j
public class Client {

    public static final ExecutorService POOL = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger COUNT = new AtomicInteger();

                @Override
                public Thread newThread(Runnable task) {
                    return new Thread(task, "business-pool-" + COUNT.getAndIncrement());
                }
            });

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            // 接受输入参数
            Scanner scanner = new Scanner(System.in);

            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicBoolean result = new AtomicBoolean();

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("customizeCodec", new MessageCodec());
                            pipeline.addLast("businessHandler", new BusinessHandler(scanner, countDownLatch, result));
                            // 客户端发送心跳包，保持活性
                            // 还是通过空闲检测实现，不过检测时间比服务端短，一般为其的二分之一
                            pipeline.addLast(new IdleStateHandler(0, 2, 0));
                            pipeline.addLast(new ChannelDuplexHandler(){
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    // 此时三秒没有写数据了
                                    if (evt instanceof IdleStateEvent && evt == IdleStateEvent.WRITER_IDLE_STATE_EVENT) {
//                                        log.info("产生写了空闲");
                                        // 向服务端发送心跳包
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", Server.PORT)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
