package com.hikvision.spring.model.service.impl;

import com.hikvision.spring.model.service.MessageService;

/**
 * @author zhangwei151
 * @description MessageServiceImpl
 * @date 2021/10/13 21:07
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public String message() {
        return "hello, spring framework";
    }
}
