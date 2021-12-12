package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.GroupQuitRequestMessage;
import com.hikviision.netty.four.GroupQuitResponseMessage;
import com.hikviision.netty.four.group.Group;
import com.hikviision.netty.four.group.GroupSessionFactory;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangwei151
 * @date 2021/12/11 19:00
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        Group group = GroupSessionFactory.getGroup().getGroup(groupName);
        GroupQuitResponseMessage responseMessage;
        if (group == null || !group.getMembers().contains(username)) {
            responseMessage = new GroupQuitResponseMessage(false, "当前用户或聊天室不存在，请检查后重试！");
        } else {
            boolean quit = GroupSessionFactory.getGroup().quit(username, groupName);
            responseMessage = quit ? new GroupQuitResponseMessage(true, "退出聊天室(" + groupName + ")成功")
                    : new GroupQuitResponseMessage(false, "退出失败");
        }
        ctx.writeAndFlush(responseMessage);
        responseMessage = new GroupQuitResponseMessage(true, "用户（" + username + "）退出了：" + groupName);
        // 退出成功时广播给聊天室其他成员
        if (responseMessage.isSuccess() && group != null) {
            GroupQuitResponseMessage finalResponseMessage = responseMessage;
            group.getMembers().stream().map(member -> SessionFactory.getSession().getChannel(member))
                    .forEach(channel -> {
                        if (channel != ctx.channel()){
                            channel.writeAndFlush(finalResponseMessage);
                        }
                    });
        }
    }
}
