package com.hikviision.netty.test;

import com.hikviision.netty.utils.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author zhangwei151
 * @date 2021/11/27 12:04
 */
public class Demo01 {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        System.out.println(buf);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        LogUtil.log(buf);

        // 测试切片
        ByteBuf bufOne = buf.slice(0, 6);
        LogUtil.log(bufOne);
        ByteBuf bufTwo = buf.slice(7, 3);
        LogUtil.log(bufTwo);

        buf.setByte(3, 'z');
        LogUtil.log(bufOne);

        // 是否buf测试bufOne是否可用
        System.out.println("释放buf");
        // 可通过retain使其计数加一，在release时不被释放
        bufOne.retain();
        buf.release();
        LogUtil.log(bufOne);
    }
}
