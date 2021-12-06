package com.hikviision.netty.four;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:31
 */
public class LoginResponseMessage extends Message{
    @Override
    public int getType() {
        return LoginResponseMessage;
    }
}
