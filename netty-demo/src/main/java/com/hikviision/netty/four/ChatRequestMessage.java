package com.hikviision.netty.four;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:35
 */
@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message{

    private String content;
    private String from;
    private String to;

    public ChatRequestMessage(String content, String from, String to) {
        this.content = content;
        this.from = from;
        this.to = to;
    }


    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
