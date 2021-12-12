package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.GroupMembersRequestMessage;
import com.hikviision.netty.four.GroupMembersResponseMessage;
import com.hikviision.netty.four.group.Group;
import com.hikviision.netty.four.group.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangwei151
 * @date 2021/12/11 18:12
 */
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    private GroupMembersResponseMessage responseMessage;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Group group = GroupSessionFactory.getGroup().getGroup(groupName);
        if (group == null) {
            // 当前聊天组不存在
            responseMessage = new GroupMembersResponseMessage(false, "当前聊天室不存在");
        } else {
            responseMessage = new GroupMembersResponseMessage(group.getMembers());
        }
        ctx.writeAndFlush(responseMessage);
    }
}
