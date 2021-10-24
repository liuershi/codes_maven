package com.hikvision.bio.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * <p>
 *     发生任意类型数据导服务端
 * </p>
 * @Author milindeyu
 * @Date 2021/10/24 5:54 下午
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream("/Users/milindeyu/thomas/project/customize/codes_maven/io/src/main/resources/server/16.jpg")) {
            Socket socket = new Socket("127.0.0.1", 9999);
            // 1.包装成数据输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            // 2.先发送文件类型
            dos.writeUTF(".jpg");
            // 3.在发送具体文件内容
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
            dos.flush();
            // 通知服务端发送完成
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
