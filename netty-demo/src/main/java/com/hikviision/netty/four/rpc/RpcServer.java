package com.hikviision.netty.four.rpc;

import com.hikviision.netty.four.handler.MessageCodec;
import com.hikviision.netty.four.rpc.handler.RequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * rpc调用服务端
 *
 * @author zhangwei151
 * @date 2021/12/18 16:11
 */
public class RpcServer {

    public static void main(String[] args) {

        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            final MessageCodec SHARED_CODEC_HANDLER = new MessageCodec();
            final RequestHandler SHARED_REQUEST_HANDLER = new RequestHandler();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 11, 4, 1, 0));
                            pipeline.addLast("loggingHandle", new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast("messageCodec", SHARED_CODEC_HANDLER);
                            pipeline.addLast("requestHandler", SHARED_REQUEST_HANDLER);
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(9999)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
