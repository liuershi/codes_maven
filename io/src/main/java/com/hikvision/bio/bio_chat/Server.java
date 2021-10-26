package com.hikvision.bio.bio_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *     服务端实现转发消息导其他客户端
 * </p>
 * @Author milindeyu
 * @Date 2021/10/24 10:10 下午
 * @Version 1.0
 */
public class Server {

    private static final Executor CLIENT_HANDLER_POOL = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger COUNT = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable task) {
                    return new Thread(task, "client-handler-" + COUNT.getAndIncrement());
                }
            });

    /**
     * 暂存在线客户端链接
     */
    protected static final List<Socket> ONLINE_CLIENTS = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            // 1.监听端口，等待客户端链接
            ServerSocket ss = new ServerSocket(9999);
            // 2.接受客户端链接
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = ss.accept();
                System.out.println("客户端：" + socket.toString() + "上线了～～");
                // 3.暂存起来并交由线程池处理
                ONLINE_CLIENTS.add(socket);
                CLIENT_HANDLER_POOL.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
