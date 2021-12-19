package com.hikviision.netty.four.rpc.service;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:33
 */
public class HelloServiceCNImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return "你好：" + name;
    }
}
