package com.hikviision.netty.four.group;

import java.util.Set;

/**
 * @author zhangwei151
 * @date 2021/12/11 15:02
 */
public interface GroupSession {

    /**
     * 创建聊天组
     * @param name 聊天组名称
     * @param members 组成员
     * @return
     */
    Group createGroup(String name, Set<String> members);

    /**
     * 获取里阿尼谈史
     * @param name
     * @return
     */
    Group getGroup(String name);

    /**
     * 检测聊天室是否存在
     * @param groupName
     * @return
     */
    boolean checkGroup(String groupName);

    /**
     * 退出当前聊天室
     * @param username 用户
     * @param groupName 聊天室
     * @return
     */
    boolean quit(String username, String groupName);
}
