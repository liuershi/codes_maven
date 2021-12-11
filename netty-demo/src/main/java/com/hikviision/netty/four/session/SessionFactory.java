package com.hikviision.netty.four.session;

/**
 * @Author milindeyu
 * @Date 2021/12/8 11:42 下午
 * @Version 1.0
 */
public abstract class SessionFactory {
    private static final Session session = new SessionImpl();

    public static Session getSession() {
        return session;
    }
}
