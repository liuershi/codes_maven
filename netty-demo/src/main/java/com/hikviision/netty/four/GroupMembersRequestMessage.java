package com.hikviision.netty.four;

import lombok.Data;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:55
 */
@Data
public class GroupMembersRequestMessage extends Message{

    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
