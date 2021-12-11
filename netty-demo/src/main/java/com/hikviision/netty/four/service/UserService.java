package com.hikviision.netty.four.service;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:22 下午
 * @Version 1.0
 */
public interface UserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean login(String username, String password);
}
