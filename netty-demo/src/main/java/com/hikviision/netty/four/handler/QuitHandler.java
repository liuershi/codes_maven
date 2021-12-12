package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangwei151
 * @date 2021/12/11 21:36
 */
@ChannelHandler.Sharable
@Slf4j
public class QuitHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道正常关闭时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SessionFactory.getSession().unbind(channel);
        log.info("通道正常关闭");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("通道异常关闭：{}", cause.getMessage());
        Channel channel = ctx.channel();
        SessionFactory.getSession().unbind(channel);
    }
}
