package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:54
 */
public class GroupQuitRequestMessage extends Message{
    @Override
    public int getType() {
        return GroupQuitRequestMessage;
    }
}
