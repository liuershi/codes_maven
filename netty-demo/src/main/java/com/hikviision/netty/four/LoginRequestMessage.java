package com.hikviision.netty.four;

import lombok.ToString;

/**
 * @author zhangwei151
 * @date 2021/12/5 0:20
 */
@ToString(callSuper = true)
public class LoginRequestMessage extends Message{

    private String username;
    private String password;
    private String nickname;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
