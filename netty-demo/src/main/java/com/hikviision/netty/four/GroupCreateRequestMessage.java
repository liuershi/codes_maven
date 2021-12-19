package com.hikviision.netty.four;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:39
 */
@Data
@AllArgsConstructor
public class GroupCreateRequestMessage extends Message{


    private static final long serialVersionUID = 3604477043597606217L;
    private String groupName;
    private Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
