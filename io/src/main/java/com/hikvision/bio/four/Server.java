package com.hikvision.bio.four;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangwei151
 * @description 使用伪异步编程的方式解决由于并发请求过多导致线程创建过多而引发的系统宕机问题（使用线程池解决）
 * @date 2021/10/19 23:38
 */
public class Server {

    private static final Executor REQUEST_HANDLER_POOL = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger count = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable task) {
                    return new Thread(task, "client-request-handler-" + count.getAndIncrement());
                }
            });

    public static void main(String[] args) {
        try {
            System.out.println("server stared...");
            // 1.创建服务器并监听指定端口
            ServerSocket ss = new ServerSocket(9999);
            while (!Thread.currentThread().isInterrupted()) {
                // 2.监听客户端请求
                Socket socket = ss.accept();
                // 3.封装成Task并交给线程池处理
                REQUEST_HANDLER_POOL.execute(new SocketTask(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
