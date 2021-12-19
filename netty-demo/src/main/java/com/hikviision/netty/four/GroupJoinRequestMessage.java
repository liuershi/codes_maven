package com.hikviision.netty.four;

import lombok.Data;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:49
 */
@Data
public class GroupJoinRequestMessage extends Message {

    private String from;
    private String groupName;

    public GroupJoinRequestMessage(String from, String groupName) {
        this.from = from;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
