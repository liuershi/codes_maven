package com.hikviision.netty.four;

import com.hikviision.netty.four.session.Session;
import com.hikviision.netty.four.session.SessionFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:01 下午
 * @Version 1.0
 */
@Slf4j
public class Client {

    private static final ExecutorService POOL = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger COUNT = new AtomicInteger();

                @Override
                public Thread newThread(Runnable task) {
                    return new Thread(task, "business-pool-" + COUNT.getAndIncrement());
                }
            });

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            // 接受输入参数
            Scanner scanner = new Scanner(System.in);

            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicBoolean result = new AtomicBoolean();

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("customizeCodec", new MessageCodec());
                            pipeline.addLast("businessHandler", new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 连接建立后发送请求
                                    POOL.execute(() -> {
                                        log.info("开始登录操作");
                                        log.info("请输入用户名：");
                                        String username = scanner.nextLine();
                                        log.info("请输入密码：");
                                        String password = scanner.nextLine();
                                        // 构建对象准备写出
                                        Message message = new LoginRequestMessage(username, password, null);
                                        ctx.writeAndFlush(message);

                                        // 等待结果响应，如果失败则关闭连接
                                        try {
                                            countDownLatch.await();
                                            if (!result.get()) {
                                                // 失败则关闭连接
                                                ctx.channel().close();
                                                POOL.shutdownNow();
                                                return;
                                            }
                                        } catch (InterruptedException e) {
                                            // ignore
                                        }

                                        // 允许继续操作
                                        log.info("登录成功，按以下格式进行操作");
                                        while (true) {
                                            log.info("=============================");
                                            log.info("send [username] [content]");
                                            log.info("gsend [group name] [content]");
                                            log.info("gcreate [group name] [m1,m2,m3...]");
                                            log.info("gmerbers [group name]");
                                            log.info("gjoin [group name]");
                                            log.info("gquit [group name]");
                                            log.info("quit");
                                            log.info("=============================");
                                            String line = scanner.nextLine();
                                            // 根据不同命令执行对应操作
                                            String[] contents = line.split(" ");
                                            switch (contents[0]) {
                                                case "send" :
                                                    ChatRequestMessage chatRequestMessage = new ChatRequestMessage(contents[contents.length - 1], username, contents[1]);
                                                    ctx.writeAndFlush(chatRequestMessage);
                                                    break;
                                                case "gsend" :
                                                    break;
                                                case "gcreate" :
                                                    break;
                                                case "gmerbers" :
                                                    break;
                                                case "gjoin" :
                                                    break;
                                                case "gquit" :
                                                    break;
                                                case "quit" :
                                                    ctx.channel().close();
                                                    POOL.shutdownNow();
                                                    return;
                                                default:
                                                    break;
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    // 接受服务端登录响应
                                    if (msg instanceof LoginResponseMessage) {
                                        LoginResponseMessage message = (LoginResponseMessage) msg;
                                        if (message.isSuccess()) {
                                            log.info("登录成功");

                                            boolean old;
                                            do {
                                                old = result.get();
                                            } while (!result.compareAndSet(old, true));
                                        } else {
                                            log.info("登录失败，失败原因为：" + message.getReason());
                                        }
                                        countDownLatch.countDown();
                                    } else if (msg instanceof ChatResponseMessage) {
                                        ChatResponseMessage responseMessage = (ChatResponseMessage) msg;
                                        if (responseMessage.isSuccess()) {
                                            log.info("receive {} message is : {}", responseMessage.getFrom(), responseMessage.getContent());
                                        } else {
                                            log.error("{}", responseMessage.getReason());
                                        }
                                    }
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", Server.PORT)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
