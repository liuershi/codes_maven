package com.hikviision.netty.four;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author milindeyu
 * @Date 2021/12/7 9:40 下午
 * @Version 1.0
 */
public class TestMessage {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.INFO),
                new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 11, 4, 1, 0),
                new MessageCodec()
        );

        // 测试编码
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123456", "张三");
        channel.writeOutbound(message);

        // 测试解码
        MessageCodec codec = new MessageCodec();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        codec.encode(null, message, null);
//        channel.writeInbound(buffer);

        // 测试半包
        ByteBuf s1 = buffer.slice(0, 100);
        buffer.retain();
        buffer.retain();
        channel.writeInbound(s1);
//        ByteBuf s2 = buffer.slice(100, buffer.readableBytes() - 100);
//        channel.writeInbound(s2);
        channel.writeInbound(buffer);
        ByteBuf copy = buffer.copy();
        channel.writeInbound(copy);
    }
}
