package com.hikvision.bio.bio_chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author milindeyu
 * @Date 2021/10/24 10:28 下午
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 1.链接服务器指定端口
            Socket socket = new Socket("127.0.0.1", 9999);
            // 2.获取输出流并包装成打印流
            new Thread(new ClientHandler.InputStreamHandler(socket.getInputStream())).start();
            new Thread(new ClientHandler.OutputStreamHandler(socket.getOutputStream())).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
