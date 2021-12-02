package com.hikviision.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Author milindeyu
 * @Date 2021/11/25 9:56 下午
 * @Version 1.0
 */
public class Test1 {
    public static void main(String[] args) {
        String type = SystemPropertyUtil.get(
                "io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
        System.out.println(type);

        System.out.println(PlatformDependent.directBufferPreferred());
        System.out.println(PlatformDependent.hasUnsafe());

        // 分配ByteBuf测试创建过程
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);
        System.out.println(ByteBufAllocator.DEFAULT.heapBuffer());
    }
}
