package com.hikvision.netty.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 了解使用NIO
 *
 * @author zhangwei151
 * @description TestByteBuffer
 * @date 2021/8/21 15:37
 */
@Slf4j
public class TestByteBuffer {

    @Test
    public void testByteBuffer() {
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);

            while (channel.read(byteBuffer) != -1) {
                // 转换读模式
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    log.debug("current char is = {}", (char) byteBuffer.get());
                }

                // 转为写模式，因为此时外层循环还需要向ByteBuffer中写数据
                byteBuffer.clear();
            }

        } catch (IOException e) {
            // ignore
        }
    }
}
