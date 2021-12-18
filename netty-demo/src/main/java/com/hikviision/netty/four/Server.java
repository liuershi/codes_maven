package com.hikviision.netty.four;

import com.hikviision.netty.four.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

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

        final LoggingHandler SHRED_LOGGING = new LoggingHandler(LogLevel.INFO);
        final MessageCodec SHARED_CODEC = new MessageCodec();
        final LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        final ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        final GroupCreateRequestMessageHandler CREATE_GROUP_HANDLER = new GroupCreateRequestMessageHandler();
        final GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        final GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
        final GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
        final GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
        final QuitHandler QUIT_HANDLER = new QuitHandler();

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
                            // 加入空闲检测处理器，可以指定读空闲和写空闲或者读写空闲的感兴趣时间，0表示不感兴趣
                            pipeline.addLast(new IdleStateHandler(5, 7, 0));
                            pipeline.addLast(new ChannelDuplexHandler(){
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    // 读空闲或写空闲会触发
                                    if (evt instanceof IdleStateEvent) {
                                        if (evt == IdleStateEvent.READER_IDLE_STATE_EVENT) {
                                            log.info("通道：{} 产生读空闲", ctx.channel());
                                            // todo 可做相应处理，例如关闭连接
                                            // 例如此时关闭客户端连接
                                            ctx.channel().close();
                                        } else if (evt == IdleStateEvent.WRITER_IDLE_STATE_EVENT) {
                                            log.info("通道：{} 产生写空闲", ctx.channel());
                                        }
                                    }
                                }
                            });
                            pipeline.addLast("loginHandler", LOGIN_HANDLER);
                            pipeline.addLast("chatHandler", CHAT_HANDLER);
                            pipeline.addLast("createGroupHandler", CREATE_GROUP_HANDLER);
                            pipeline.addLast("groupChatHandler", GROUP_CHAT_HANDLER);
                            pipeline.addLast("groupMembersHandler", GROUP_MEMBERS_HANDLER);
                            pipeline.addLast("groupJoinHandler", GROUP_JOIN_HANDLER);
                            pipeline.addLast("groupQuitHandler", GROUP_QUIT_HANDLER);
                            pipeline.addLast("quitHandler", QUIT_HANDLER);
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
