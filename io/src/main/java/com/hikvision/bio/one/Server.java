package com.hikvision.bio.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhangwei151
 * @description 演示BIO同步机制
 * @date 2021/10/19 22:48
 */
public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("服务器已启动...");
            // 1.服务器启动并监听指定端口
            ServerSocket ss = new ServerSocket(9999);
            // 2.阻塞并监听客户端请求
            Socket socket = ss.accept();
            // 3.获取输入流并包装
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 4.读取客户端请求数据
            String content;

            /**
             * 客户端打印流使用print()方法时，发送的数据中不包含换行符，此时服务器接收到数据单但不认为
             * 此时数据完成，还会继续等待，而客户端执行关闭后方法结束退出，而服务器等待导致抛出Connection reset异常
             *
             * 此例子说明：readLine(）方法时同步阻塞的
             *
             * 解决：
             *  1.客户端使用换行打印方法println()
             *  2.客户端请求数据中结尾带上换行符\n
             */
            while ((content = br.readLine()) != null) {
                System.out.println("客户端请求数据：" + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
