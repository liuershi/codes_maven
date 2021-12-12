package com.hikviision.netty.four;

/**
 * 客户端心跳包
 *
 * @author zhangwei151
 * @date 2021/12/12 15:59
 */
public class PingMessage extends Message {
    @Override
    public int getType() {
        return PingMessage;
    }
}
