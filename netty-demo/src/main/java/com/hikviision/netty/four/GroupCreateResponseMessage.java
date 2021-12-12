package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:48
 */
public class GroupCreateResponseMessage extends AbstractResponseMessage {

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getType() {
        return GroupCreateResponseMessage;
    }
}
