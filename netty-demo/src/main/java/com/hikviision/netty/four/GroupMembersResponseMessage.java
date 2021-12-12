package com.hikviision.netty.four;

import lombok.Data;

import java.util.Set;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:55
 */
@Data
public class GroupMembersResponseMessage extends AbstractResponseMessage {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    public GroupMembersResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getType() {
        return GroupMembersResponseMessage;
    }
}
