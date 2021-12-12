package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.GroupJoinRequestMessage;
import com.hikviision.netty.four.GroupJoinResponseMessage;
import com.hikviision.netty.four.group.Group;
import com.hikviision.netty.four.group.GroupSessionFactory;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangwei151
 * @date 2021/12/11 18:24
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        String groupName = msg.getGroupName();
        // 检测用户或聊天室是否存在
        GroupJoinResponseMessage responseMessage;
        if (!SessionFactory.getSession().checkUser(from) || !GroupSessionFactory.getGroup().checkGroup(groupName)) {
            responseMessage = new GroupJoinResponseMessage(false, "用户或者聊天室不存在");
        } else {
            Group group = GroupSessionFactory.getGroup().getGroup(groupName);
            responseMessage = group.getMembers().add(from) ?
                    new GroupJoinResponseMessage(true, from + "加入聊天室成功")
                    :
                    new GroupJoinResponseMessage(false, from + "已存在此聊天室");

        }
        ctx.writeAndFlush(responseMessage);
        // 如果加入成功则通知聊天室其他成员某用户加入
        if (responseMessage.isSuccess()) {
            Channel currentChannel = ctx.channel();
            responseMessage = new GroupJoinResponseMessage(true, "用户：" + from + " 加入了聊天室（" + groupName + ")");
            GroupJoinResponseMessage finalResponseMessage = responseMessage;
            GroupSessionFactory.getGroup().getGroup(groupName).getMembers().forEach(member -> {
                Channel channel = SessionFactory.getSession().getChannel(member);
                if (channel != currentChannel) {
                    channel.writeAndFlush(finalResponseMessage);
                }
            });
        }
    }
}
