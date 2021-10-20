package com.hikvision.bio.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author zhangwei151
 * @description Client
 * @date 2021/10/19 22:52
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 1.启动并请求服务器连接
            Socket socket = new Socket("127.0.0.1", 9999);
            // 2.获取输出流并写数据
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            // 3.像服务器写数据
            ps.print("hello, 你好, wwa\n");
            ps.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
