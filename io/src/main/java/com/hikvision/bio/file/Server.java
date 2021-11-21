package com.hikvision.bio.file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *     接受客户端传送过来的任意类型数据并保存在本地
 * </p>
 * @Author milindeyu
 * @Date 2021/10/24 5:48 下午
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                new ServerTask(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
