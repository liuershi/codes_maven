package com.hikvision.bio.four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author zhangwei151
 * @description SocketTask
 * @date 2021/10/19 23:41
 */
public class SocketTask implements Runnable{

    private Socket socket;

    public SocketTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 1.获取输入流并包装读取内容
        try {
            System.out.println("接受客户端请求，客户端地址为：" + socket.getRemoteSocketAddress().toString());
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String content = null;
            while ((content = br.readLine()) != null) {
                System.out.println("receive client message : " + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
