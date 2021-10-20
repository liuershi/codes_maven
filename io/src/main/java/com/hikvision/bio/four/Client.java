package com.hikvision.bio.four;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author zhangwei151
 * @description Client
 * @date 2021/10/19 23:47
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 1.创建请求
            Socket socket = new Socket("127.0.0.1", 9999);
            // 2.获取输出流并包装
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            // 3.向服务器发起请求
            Scanner scanner = new Scanner(System.in);
            while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
                System.out.print("请输入：");
                String msg = scanner.nextLine();
                ps.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
