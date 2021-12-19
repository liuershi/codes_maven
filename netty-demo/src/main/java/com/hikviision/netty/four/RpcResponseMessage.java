package com.hikviision.netty.four;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:09
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message{

    private Object returnValue;

    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}
