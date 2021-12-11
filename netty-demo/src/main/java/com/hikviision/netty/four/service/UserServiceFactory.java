package com.hikviision.netty.four.service;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:26 下午
 * @Version 1.0
 */
public abstract class UserServiceFactory {

    private static UserService userService = new UserServiceImpl();

    public static UserService factoryBuild() {
        return userService;
    }
}
