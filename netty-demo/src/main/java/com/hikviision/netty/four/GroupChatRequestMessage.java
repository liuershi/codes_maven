package com.hikviision.netty.four;

import lombok.Data;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:54
 */
@Data
public class GroupChatRequestMessage extends Message{

    private String from;
    private String groupName;
    private String content;

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.from = from;
        this.groupName = groupName;
        this.content = content;
    }

    @Override
    public int getType() {
        return GroupChatRequestMessage;
    }
}
