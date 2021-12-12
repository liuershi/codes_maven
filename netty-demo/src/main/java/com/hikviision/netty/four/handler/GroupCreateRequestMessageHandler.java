package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.GroupCreateRequestMessage;
import com.hikviision.netty.four.GroupCreateResponseMessage;
import com.hikviision.netty.four.group.Group;
import com.hikviision.netty.four.group.GroupSessionFactory;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.Set;

/**
 * @author zhangwei151
 * @date 2021/12/11 15:14
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 存储
        Group group = GroupSessionFactory.getGroup().createGroup(groupName, members);
        // 构建响应对象
        GroupCreateResponseMessage responseMessage;
        if (group == null) {
            String username = SessionFactory.getSession().getUsername(ctx.channel());
            responseMessage = new GroupCreateResponseMessage(true, "聊天室(" + groupName + ")创建成功，您被：" + username + " 拉入了聊天室");
            // 向群组成功广播消息
            members.stream().map(member -> SessionFactory.getSession().getChannel(member))
                    .filter(Objects::nonNull)
                    .forEach(item -> {
                        if (item != ctx.channel()) {
                            item.writeAndFlush(responseMessage);
                        }
                    });
        } else {
            responseMessage = new GroupCreateResponseMessage(false,  "聊天室(" + groupName + ")已经存在");
        }
        ctx.writeAndFlush(new GroupCreateResponseMessage(true, "聊天室(" + groupName + ")创建成功"));
    }
}
