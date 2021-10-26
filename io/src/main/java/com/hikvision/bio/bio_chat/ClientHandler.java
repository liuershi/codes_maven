package com.hikvision.bio.bio_chat;

import java.io.*;
import java.util.Scanner;

/**
 * @Author milindeyu
 * @Date 2021/10/24 11:04 下午
 * @Version 1.0
 */
public class ClientHandler{

    static class InputStreamHandler implements Runnable {

        private InputStream is;

        public InputStreamHandler(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String content;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    content = br.readLine();
                    System.out.println("接收到消息：" + content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     static class OutputStreamHandler implements Runnable {

        private OutputStream os;

         public OutputStreamHandler(OutputStream os) {
             this.os = os;
         }

         @Override
         public void run() {
             PrintWriter pw = new PrintWriter(os);
             Scanner scanner = new Scanner(System.in);
             while (!Thread.currentThread().isInterrupted()) {
                 System.out.print("请输入：");
                 String line = scanner.nextLine();
                 pw.println(line);
                 pw.flush();
             }
         }
     }
}
