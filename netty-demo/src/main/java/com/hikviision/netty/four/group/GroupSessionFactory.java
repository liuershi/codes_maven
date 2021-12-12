package com.hikviision.netty.four.group;

/**
 * @author zhangwei151
 * @date 2021/12/11 15:02
 */
public abstract class GroupSessionFactory {

    private static final GroupSession GROUP_SESSION = new GroupSessionImpl();

    public static GroupSession getGroup() { return GROUP_SESSION; }
}
