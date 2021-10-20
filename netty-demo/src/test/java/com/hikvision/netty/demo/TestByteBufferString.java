package com.hikvision.netty.demo;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangwei151
 * @description 将字符串转ByteBuffer的方法
 * @date 2021/8/21 21:15
 */
public class TestByteBufferString {

    /**
     * 方式一：使用字符串的getBytes()即可
     */
    @Test
    public void testStringOnw() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        String str = "hello";
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        read(byteBuffer);
    }

    /**
     * 方式二：使用nio包中提供的标准字符集类的encoder()方法
     */
    @Test
    public void testStringTwo() {
        String str = "thomas";
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(str);
        read(byteBuffer);
    }

    /**
     * 方式三：使用wrap方法
     */
    @Test
    public void testStringThree() {
        ByteBuffer byteBuffer = ByteBuffer.wrap("world".getBytes(StandardCharsets.UTF_8));
        read(byteBuffer);
        System.out.println();
        writeOne(byteBuffer);
        byteBuffer.flip();
        writeTwo(byteBuffer);
    }

    private void read(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
    }

    public void writeOne(ByteBuffer byteBuffer) {
        byte[] array = byteBuffer.array();
        String result = new String(array, StandardCharsets.UTF_8);
        System.out.println("result = " + result);
    }

    public void writeTwo(ByteBuffer byteBuffer){
        String result = StandardCharsets.UTF_8.decode(byteBuffer).toString();
        System.out.println("result = " + result);
    }
}
