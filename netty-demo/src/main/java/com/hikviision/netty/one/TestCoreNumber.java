package com.hikviision.netty.one;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author milindeyu
 * @Date 2021/11/17 4:00 下午
 * @Version 1.0
 */
@Slf4j
public class TestCoreNumber {

    public static void main(String[] args) {
        // 测试系统核心数
        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println(NettyRuntime.availableProcessors());


        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());

        // 执行普通任务
        eventExecutors.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });


        // 执行定时任务
        log.info("current time");
        eventExecutors.next().scheduleAtFixedRate(() -> {
            log.info("running scheduler task...");
        }, 3, 5, TimeUnit.SECONDS);
    }
}
