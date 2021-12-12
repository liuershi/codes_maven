package com.hikviision.netty.four.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author milindeyu
 * @Date 2021/12/8 11:43 下午
 * @Version 1.0
 */
public class SessionImpl implements Session {

    /**
     * 绑定通道与用户名直接的映射
     */
    private static Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    /**
     * 绑定用户名与通道之间的映射
     */
    private static Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();
    /**
     * 绑定通道对应的自定义属性
     */
    private static Map<Channel, Map<String, Object>> channelAttrsMap = new ConcurrentHashMap<>();


    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
        channelAttrsMap.put(channel, new HashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.get(channel);
        Optional.ofNullable(username).ifPresent(item -> usernameChannelMap.remove(item));
        channelUsernameMap.remove(channel);
        channelAttrsMap.remove(channel);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public String getUsername(Channel channel) {
        return channelUsernameMap.get(channel);
    }

    @Override
    public boolean checkUser(String username) {
        return usernameChannelMap.containsKey(username);
    }
}
