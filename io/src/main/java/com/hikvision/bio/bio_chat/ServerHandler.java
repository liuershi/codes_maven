package com.hikvision.bio.bio_chat;

import java.io.*;
import java.net.Socket;

/**
 * @Author milindeyu
 * @Date 2021/10/24 10:15 下午
 * @Version 1.0
 */
public class ServerHandler implements Runnable {

    private final Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 1.根据socket包装输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 2.读取客户端内容
            String content;
            while ((content = br.readLine()) != null) {
                for (Socket client : Server.ONLINE_CLIENTS) {
                    // 3.将数据发送至其他客户端
                    if (client != socket) {
                        OutputStream os = client.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        pw.println(content);
                        pw.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 离线或异常时移除
            Server.ONLINE_CLIENTS.remove(socket);
        }

    }
}
