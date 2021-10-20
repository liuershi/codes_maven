package com.hikvision.spring.service.impl;

import com.hikvision.spring.service.HelloService;

/**
 * @author zhangwei151
 * @description HelloServiceImpl
 * @date 2021/10/18 11:07
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String name) {
        String hello = "hello " + name;
        System.out.println(hello);
        return hello;
    }
}
