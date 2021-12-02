package com.hikviision.netty.one;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Optional;

/**
 * @author zhangwei151
 * @description Server
 * @date 2021/10/21 17:02
 */
public class Server {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "9999"));

    public static void main(String[] args) throws CertificateException, SSLException {
        // 1.配置ssl
        SslContext sslContext;
        if (SSL) {
            SelfSignedCertificate ssl = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(ssl.certificate(), ssl.privateKey()).build();
        } else {
            sslContext = null;
        }

        // 2.配置服务
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
//                    .handler(new LoggingHandler())
                    .handler(new ServerSimpleHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            Optional.ofNullable(sslContext).ifPresent(context -> pipeline.addLast(sslContext.newHandler(ch.alloc())));
                            pipeline.addLast("serverHandler", new ServerHandler());
//                            pipeline.addLast("testHandler", new TestInBound());
                        }
                    });

            // 3.启动服务
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(PORT)).sync();
            // 4.等待服务关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 5.优雅关闭
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
