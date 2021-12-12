package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.GroupChatRequestMessage;
import com.hikviision.netty.four.GroupChatResponseMessage;
import com.hikviision.netty.four.group.Group;
import com.hikviision.netty.four.group.GroupSessionFactory;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.Optional;

/**
 * @author zhangwei151
 * @date 2021/12/11 14:57
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        GroupChatResponseMessage responseMessage = new GroupChatResponseMessage(msg.getFrom(), msg.getContent());
        Group group = GroupSessionFactory.getGroup().getGroup(groupName);
        Optional.ofNullable(group).ifPresent(
                item ->
                        item.getMembers().stream().map(member -> SessionFactory.getSession().getChannel(member))
                            .filter(Objects::nonNull)
                            .forEach(channel -> {
                                if (channel != ctx.channel()) {
                                    channel.writeAndFlush(responseMessage);
                                }
                            })
        );
    }
}
