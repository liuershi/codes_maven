package com.hikvision.bio.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhangwei151
 * @description 实现服务端可接收多客户端的请求
 * @date 2021/10/19 22:48
 */
public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("服务器已启动...");
            // 1.服务器启动并监听指定端口
            ServerSocket ss = new ServerSocket(9999);
            while (true) {
                // 2.阻塞并监听客户端请求
                Socket socket = ss.accept();
                new Thread(() -> {
                    try {
                        // 3.获取输入流并包装
                        InputStream is = socket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        // 4.读取客户端请求数据
                        String content;

                        while ((content = br.readLine()) != null) {
                            System.out.println("客户端请求数据：" + content);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
