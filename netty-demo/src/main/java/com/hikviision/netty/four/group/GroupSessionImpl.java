package com.hikviision.netty.four.group;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangwei151
 * @date 2021/12/11 15:02
 */
public class GroupSessionImpl implements GroupSession {

    private static final Map<String, Group> GROUPS = new ConcurrentHashMap<>();


    @Override
    public Group createGroup(String name, Set<String> members) {
        return GROUPS.putIfAbsent(name, new Group(name, members));
    }

    @Override
    public Group getGroup(String name) {
        return GROUPS.get(name);
    }

    @Override
    public boolean checkGroup(String groupName) {
        return GROUPS.containsKey(groupName);
    }

    @Override
    public boolean quit(String username, String groupName) {
        return GROUPS.get(groupName).getMembers().remove(username);
    }
}
