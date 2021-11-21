package com.hikvision.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author zhangwei151
 * @date 2021/11/13 22:20
 */
public class TestTransfer {

    public static void main(String[] args) {
        testTransferFrom();
        testTransferTo();
    }

    /**
     * 与transferFrom方法相反，transferTo主要是源channel调用
     */
    private static void testTransferTo() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("io/data01.txt");
            FileChannel isc = fis.getChannel();
            fos = new FileOutputStream("io/data03.txt");
            FileChannel osc = fos.getChannel();

            isc.transferTo(isc.position(), isc.size(), osc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
     * 测试transferFrom方法，实现文件的复制
     */
    private static void testTransferFrom() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("io/data01.txt");
            FileChannel isc = fis.getChannel();
            fos = new FileOutputStream("io/data02.txt");
            FileChannel osc = fos.getChannel();

            osc.transferFrom(isc, isc.position(), isc.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
}
