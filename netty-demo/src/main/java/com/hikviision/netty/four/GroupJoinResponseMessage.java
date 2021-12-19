package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:53
 */
public class GroupJoinResponseMessage extends AbstractResponseMessage {

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupJoinResponseMessage;
    }
}
