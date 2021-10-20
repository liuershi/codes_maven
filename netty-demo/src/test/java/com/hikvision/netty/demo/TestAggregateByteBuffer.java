package com.hikvision.netty.demo;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 测试多个ByteBuffer聚合使用
 *
 * @author zhangwei151
 * @description TestAggregateByteBuffer
 * @date 2021/8/21 22:24
 */
public class TestAggregateByteBuffer {

    @Test
    public void test() {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("张三");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("世界");

        try (FileChannel channel = new RandomAccessFile("world.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException e) {
        }
    }
}
