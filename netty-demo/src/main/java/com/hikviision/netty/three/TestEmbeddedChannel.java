package com.hikviision.netty.three;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * EmbeddedChannel是netty提供的快速测试入站和出站事件的工具类
 *
 * @author zhangwei151
 * @date 2021/11/21 22:49
 */
@Slf4j
public class TestEmbeddedChannel {
    public static void main(String[] args) {
        // 声明两个入站处理器
        ChannelInboundHandlerAdapter inboundHandlerAdapterOne = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof  ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) msg;
                    log.info("in bound message one is : {}", byteBuf.toString(Charset.defaultCharset()));
                }
                super.channelRead(ctx, msg);
            }
        };
        ChannelInboundHandlerAdapter inboundHandlerAdapterTwo = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof  ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) msg;
                    log.info("in bound message two is : {}", byteBuf.toString(Charset.defaultCharset()));
                }
                super.channelRead(ctx, msg);
            }
        };
        // 同样的声明两个出站处理器
        ChannelOutboundHandlerAdapter outboundHandlerAdapterOne = new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (msg instanceof  ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) msg;
                    log.info("out bound message one is : {}", byteBuf.toString(Charset.defaultCharset()));
                }
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter outboundHandlerAdapterTwo = new ChannelOutboundHandlerAdapter(){
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (msg instanceof  ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) msg;
                    log.info("out bound message two is : {}", byteBuf.toString(Charset.defaultCharset()));
                }
                super.write(ctx, msg, promise);
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(inboundHandlerAdapterOne, inboundHandlerAdapterTwo, outboundHandlerAdapterOne, outboundHandlerAdapterTwo);
        // 模拟入站事件
//        embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello world".getBytes(StandardCharsets.UTF_8)));
        // 模拟出站事件
        embeddedChannel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("out bound".getBytes(StandardCharsets.UTF_8)));
    }
}
