package com.hikviision.netty.three;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 基于http协议，使用netty自定义的解码器来实现处理http请求
 *
 * @Author milindeyu
 * @Date 2021/12/2 10:17 下午
 * @Version 1.0
 */
@Slf4j
public class TestHttp {

    private void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("loggingHandler", new LoggingHandler(LogLevel.INFO));
                            // 添加netty提供的处理http请求的编解码器
                            pipeline.addLast("httpCodec", new HttpServerCodec());
                            // 然后添加自定义的处理器
                            pipeline.addLast("customizeHandler", new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    // 打印请求参数
                                    log.info("request method {} and uri {}", msg.method(), msg.uri());

                                    // ...处理业务，一般的http服务器处理
                                    // 然后响应请求，通过DefaultFullHttpResponse对象构建响应，这个对象在经过HttpServerCodec处理时
                                    // 会自动转换为ByteBuf
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                    byte[] content = "<h1>hello, i am fishball!</h1>".getBytes(StandardCharsets.UTF_8);
                                    response.content().writeBytes(content);
                                    // 指定响应数据长度，否则浏览器不知道数据长度一直等待
                                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.length);

                                    // 写出到浏览器
                                    ctx.writeAndFlush(response);
                                }
                            });

                            /*pipeline.addLast("customizeHandler", new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info("current msg type is {}", msg.getClass());
                                    // 打印了两次，数据类型分别为DefaultHttpRequest、LastHttpContent$1
                                    // 这是由于对于http请求，netty会将请求处理成这两个对象，不管是get还是post请求，都会
                                    // 处理成请求头对象DefaultHttpRequest和请求体对象LastHttpContent

                                    // 通过类型判断做不同处理
                                    if (msg instanceof HttpRequest) {
                                        HttpRequest request = (HttpRequest) msg;
                                        log.info("request method {} and uri {}", request.getMethod().name(), request.getUri());
                                    } else if (msg instanceof HttpContent) {
                                        log.info("this is content");
                                    }

                                    // 判断比较冗余，可通过SimpleChannelInboundHandler指定泛型来处理特定数据类型
                                }
                            });*/
                        }
                    });

            // 启动服务
            log.info("start run server");
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(9999)).sync();
            log.info("server start successful");
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TestHttp().run();
    }
}
