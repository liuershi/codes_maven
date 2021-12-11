package com.hikviision.netty.four.session;

import io.netty.channel.Channel;

/**
 * @Author milindeyu
 * @Date 2021/12/8 11:42 下午
 * @Version 1.0
 */
public interface Session {

    /**
     * 绑定会话
     * @param channel 通道
     * @param username 用户名
     */
    void bind(Channel channel, String username);

    /**
     * 解绑
     * @param channel
     */
    void unbind(Channel channel);

    /**
     * 获取通道
     * @param username 用户名
     * @return
     */
    Channel getChannel(String username);
}
