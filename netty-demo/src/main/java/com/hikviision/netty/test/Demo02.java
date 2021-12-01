package com.hikviision.netty.test;

import com.hikviision.netty.utils.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * 测试
 *
 * @author zhangwei151
 * @date 2021/11/27 15:50
 */
public class Demo02 {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 但是使用ByteBuf的writeBytes也可将数据从一个ByteBuf写进来，但是底层会发生数据拷贝
        // 在数据量比较大的时候还是挺消耗性能的
        /*ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(buf1).writeBytes(buf2);
        LogUtil.log(buf);*/



        // 对于多个ByteBuf组合起来，推荐使用CompositeByteBuf，其底层不会产生数据拷贝
        // 它相当于将多个真实的ByteBuf组合成一个虚拟的缓冲区，实际的存储还是ByteBuf
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeByteBuf.addComponents(true, buf1, buf2);
        LogUtil.log(compositeByteBuf);
    }
}
