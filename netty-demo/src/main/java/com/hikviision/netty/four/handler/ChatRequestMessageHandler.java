package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.ChatRequestMessage;
import com.hikviision.netty.four.ChatResponseMessage;
import com.hikviision.netty.four.Message;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author milindeyu
 * @Date 2021/12/9 12:58 上午
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage message) throws Exception {
        // 消息转发请求
        // 判断目的channel是否在线
        Channel channel = SessionFactory.getSession().getChannel(message.getTo());
        if (channel == null) {
            // 客户端离线或不在线时
            ChatResponseMessage responseMessage = new ChatResponseMessage(false, "用户不在线或已经离线");
            ctx.writeAndFlush(responseMessage);
        } else {
            ChatResponseMessage responseMessage = new ChatResponseMessage(message.getFrom(), message.getContent());
            channel.writeAndFlush(responseMessage);
        }
    }
}
