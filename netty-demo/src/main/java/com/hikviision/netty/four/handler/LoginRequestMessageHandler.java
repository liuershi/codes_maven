package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.LoginRequestMessage;
import com.hikviision.netty.four.LoginResponseMessage;
import com.hikviision.netty.four.Message;
import com.hikviision.netty.four.service.UserServiceFactory;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author milindeyu
 * @Date 2021/12/9 12:56 上午
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage message) throws Exception {
        // 登录请求
        // 检测用户名和密码
        boolean login = UserServiceFactory.factoryBuild().login(message.getUsername(), message.getPassword());
        LoginResponseMessage loginResponseMessage;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), message.getUsername());
            loginResponseMessage = new LoginResponseMessage(login, "登录成功");
        } else {
            loginResponseMessage = new LoginResponseMessage(login, "账号或密码错误");
        }
        ctx.writeAndFlush(loginResponseMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SessionFactory.getSession().unbind(channel);
    }
}
