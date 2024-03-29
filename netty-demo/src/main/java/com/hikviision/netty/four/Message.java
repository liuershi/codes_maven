package com.hikviision.netty.four;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei151
 * @date 2021/12/4 23:12
 */
@Data
public abstract class Message implements Serializable {

    /**
     * 消息序列标识
     */
    protected int sequenceId;

    /**
     * 消息类型，即指令类型
     */
    private int messageType;

    /**
     * 获取具体操作类型
     * @return
     */
    public abstract int getMessageType();


    public static int LoginRequestMessage = 0;
    public static int LoginResponseMessage = 1;
    public static int ChatRequestMessage  = 2;
    public static int ChatResponseMessage  = 3;
    public static int GroupCreateRequestMessage = 4;
    public static int GroupCreateResponseMessage = 5;
    public static int GroupJoinRequestMessage = 6;
    public static int GroupJoinResponseMessage = 7;
    public static int GroupQuitRequestMessage = 8;
    public static int GroupQuitResponseMessage = 9;
    public static int GroupChatRequestMessage = 10;
    public static int GroupChatResponseMessage = 11;
    public static int GroupMembersRequestMessage = 12;
    public static int GroupMembersResponseMessage = 13;

    /**
     * 新增RPC请求消息类型
     */
    public static int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static int RPC_MESSAGE_TYPE_RESPONSE = 102;


    public static int PingMessage = 14;
    public static int PongMessage = 15;

    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestMessage, com.hikviision.netty.four.LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, com.hikviision.netty.four.LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, com.hikviision.netty.four.ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, com.hikviision.netty.four.ChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, com.hikviision.netty.four.GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, com.hikviision.netty.four.GroupCreateResponseMessage.class);
        messageClasses.put(GroupJoinRequestMessage, com.hikviision.netty.four.GroupJoinRequestMessage.class);
        messageClasses.put(GroupJoinResponseMessage, com.hikviision.netty.four.GroupJoinResponseMessage.class);
        messageClasses.put(GroupQuitRequestMessage, com.hikviision.netty.four.GroupQuitRequestMessage.class);
        messageClasses.put(GroupQuitResponseMessage, com.hikviision.netty.four.GroupQuitResponseMessage.class);
        messageClasses.put(GroupChatRequestMessage, com.hikviision.netty.four.GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, com.hikviision.netty.four.GroupChatResponseMessage.class);
        messageClasses.put(GroupMembersRequestMessage, com.hikviision.netty.four.GroupMembersRequestMessage.class);
        messageClasses.put(GroupMembersResponseMessage, com.hikviision.netty.four.GroupMembersResponseMessage.class);

        messageClasses.put(PingMessage, com.hikviision.netty.four.PingMessage.class);

        // 序列化
        messageClasses.put(RPC_MESSAGE_TYPE_REQUEST, com.hikviision.netty.four.RpcRequestMessage.class);
        messageClasses.put(RPC_MESSAGE_TYPE_RESPONSE, com.hikviision.netty.four.RpcResponseMessage.class);
    }

    public static Class<?> getMessageClass(int message) {
        return messageClasses.get(message);
    }
}
