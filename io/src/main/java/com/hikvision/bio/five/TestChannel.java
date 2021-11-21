package com.hikvision.bio.five;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p>
 *     测试channel的使用
 * </p>
 * @Author milindeyu
 * @Date 2021/10/26 11:32 下午
 * @Version 1.0
 */
public class TestChannel {
    public static void main(String[] args) {
//        write("data1.txt");
//        read("data1.txt");
        copy("/Users/milindeyu/thomas/dev/environment/maven_repository/antlr/antlr/2.7.2/antlr-2.7.2.jar",
                "/Users/milindeyu/thomas/dev/environment/maven_repository/antlr/antlr/2.7.2/antlr-2.7.2-new.jar");
    }

    /**
     * 通过FileChannel实现文件的复制
     * @param srcPath 源文件路径
     * @param destPath 目标文件路径
     */
    private static void copy(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            // 1.创建对应的流并获取对应的通道(Channel)
            fis = new FileInputStream(srcPath);
            fos = new FileOutputStream(destPath);
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            // 2.创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 3.循环读取文件内容
            int flag = -1;
            while (!Thread.currentThread().isInterrupted()) {
                flag = fisChannel.read(buffer);
                if (flag == -1) {
                    break;
                }
                buffer.flip();
                fosChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从指定文件中读取内容
     * @param name 文件名
     */
    private static void read(String name) {
        try {
            FileInputStream fis = new FileInputStream(name);
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);

            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.remaining()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入内容到指定文件
     * @param name 文件名
     */
    private static void write(String name) {
        // 1.创建文件输出流
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(name);
            // 2.获取对应的Channel
            FileChannel channel = fos.getChannel();
            // 3.创建buffer并像channel中写数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
            // 4.切换模式
            buffer.flip();
            channel.write(buffer);
            // 5.关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
