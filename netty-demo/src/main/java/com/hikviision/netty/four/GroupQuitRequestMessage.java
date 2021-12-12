package com.hikviision.netty.four;

import lombok.Data;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:54
 */
@Data
public class GroupQuitRequestMessage extends Message{

    private String username;
    private String groupName;

    public GroupQuitRequestMessage(String username, String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    @Override
    public int getType() {
        return GroupQuitRequestMessage;
    }
}
