package com.hikviision.netty.four.rpc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangwei151
 * @date 2021/12/19 0:38
 */
public class SequenceFactory {
    private static AtomicInteger integer = new AtomicInteger();

    public static Integer getNextId() {
        return integer.incrementAndGet();
    }
}
