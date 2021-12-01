package com.hikviision.netty.three;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 测试通过redis协议向Redis中写入命令
 *
 * @author zhangwei151
 * @date 2021/11/30 22:26
 */
public class TestRedis {

    private static final String REMOTE_HOST = "192.168.13.13";

    private static final int PORT = 6379;

    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        // 换行符对应的ascii码值
        final byte[] LINE = {13, 10};

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast("testRedisHandler", new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 连接建立之后向Redis中写入命令
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    // 命令以换行符结尾，格式为：
                                    // *3 $3 $4 $8
                                    // *3：标识整个命令有三段
                                    // $3 $4 $8 : 分别标识每段命令的字符数，例如：set name zhangsan
                                    buffer.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$8".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("zhangsan".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    ctx.writeAndFlush(buffer);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if (msg instanceof ByteBuf) {
                                        ByteBuf receive = (ByteBuf) msg;
                                        System.out.println("receive message : " + receive.toString(StandardCharsets.UTF_8));
                                    }
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(REMOTE_HOST, PORT)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
