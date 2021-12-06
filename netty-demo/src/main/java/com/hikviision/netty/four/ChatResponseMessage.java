package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:37
 */
public class ChatResponseMessage extends Message{
    @Override
    public int getType() {
        return ChatResponseMessage;
    }
}
