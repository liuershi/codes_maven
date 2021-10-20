package com.hikvision.netty.demo;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 测试分散读，即将数据一次性读取到多个ByteBuffer中
 *
 * @author zhangwei151
 * @description TestScatteredByteBuffer
 * @date 2021/8/21 22:09
 */
public class TestScatteredByteBuffer {

    @Test
    public void test() {
        try (FileChannel channel = new RandomAccessFile("number.txt", "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);

            channel.read(new ByteBuffer[]{b1, b2, b3});

            read(b1);
            read(b2);
            read(b3);
        } catch (IOException e) {
        }

    }
    private void read(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
        System.out.println();
    }
}
