package com.hikviision.netty.four;

import com.hikviision.netty.four.handler.ChatRequestMessageHandler;
import com.hikviision.netty.four.handler.LoginRequestMessageHandler;
import com.hikviision.netty.four.service.UserServiceFactory;
import com.hikviision.netty.four.session.Session;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @Author milindeyu
 * @Date 2021/12/8 9:53 下午
 * @Version 1.0
 */
@Slf4j
public class Server {
    protected static final int PORT = 9999;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        LoggingHandler SHRED_LOGGING = new LoggingHandler(LogLevel.INFO);
        MessageCodec SHARED_CODEC = new MessageCodec();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 11, 4, 1, 0));
                            pipeline.addLast("loggingHandler", SHRED_LOGGING);
                            pipeline.addLast("customizeCodec", SHARED_CODEC);
                            pipeline.addLast("loginHandler", LOGIN_HANDLER);
                            pipeline.addLast("chatHandler", CHAT_HANDLER);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(PORT)).sync();
            log.info("Service startup completed");
            log.info("Waiting for the guest client to connect...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
