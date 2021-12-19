package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:54
 */
public class GroupQuitResponseMessage extends AbstractResponseMessage {

    public GroupQuitResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupQuitResponseMessage;
    }
}
