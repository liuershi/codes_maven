package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:35
 */
public class ChatRequestMessage extends Message{
    @Override
    public int getType() {
        return ChatRequestMessage;
    }
}
