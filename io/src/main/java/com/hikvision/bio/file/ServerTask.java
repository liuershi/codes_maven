package com.hikvision.bio.file;

import org.omg.CORBA.portable.OutputStream;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @Author milindeyu
 * @Date 2021/10/24 5:50 下午
 * @Version 1.0
 */
public class ServerTask extends Thread{
    private Socket socket;

    public ServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            // 1.根据socket包装成输入流
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            // 2.先获取文件类型
            String suffix = dis.readUTF();
            // 3.读取文件内容并保存在本地
            String fileName = UUID.randomUUID().toString() + suffix;
            FileOutputStream fos = new FileOutputStream("/Users/milindeyu/thomas/project/customize/codes_maven/io/src/main/resources/client/" + fileName);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
