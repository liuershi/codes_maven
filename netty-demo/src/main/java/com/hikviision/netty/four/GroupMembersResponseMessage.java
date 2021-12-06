package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:55
 */
public class GroupMembersResponseMessage extends Message {
    @Override
    public int getType() {
        return GroupMembersResponseMessage;
    }
}
