package com.hikviision.netty.four.handler;

import com.hikviision.netty.four.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @date 2021/12/12 15:54
 */
@Slf4j
public class BusinessHandler extends ChannelInboundHandlerAdapter {
    private final Scanner scanner;
    private final CountDownLatch countDownLatch;
    private final AtomicBoolean result;

    public BusinessHandler(Scanner scanner, CountDownLatch countDownLatch, AtomicBoolean result) {
        this.scanner = scanner;
        this.countDownLatch = countDownLatch;
        this.result = result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 连接建立后发送请求
        Client.POOL.execute(() -> {
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
                    Client.POOL.shutdownNow();
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
                log.info("gmembers [group name]");
                log.info("gjoin [group name]");
                log.info("gquit [group name]");
                log.info("quit");
                log.info("=============================");
                String line = scanner.nextLine();
                // 根据不同命令执行对应操作
                String[] contents = line.split(" ");
                switch (contents[0]) {
                    case "send":
                        ChatRequestMessage chatRequestMessage = new ChatRequestMessage(contents[contents.length - 1], username, contents[1]);
                        ctx.writeAndFlush(chatRequestMessage);
                        break;
                    case "gsend":
                        GroupChatRequestMessage groupChatRequestMessage = new GroupChatRequestMessage(username, contents[1], contents[contents.length - 1]);
                        ctx.writeAndFlush(groupChatRequestMessage);
                        break;
                    case "gcreate":
                        GroupCreateRequestMessage groupCreateRequestMessage = new GroupCreateRequestMessage(contents[1], Arrays.stream(contents[contents.length - 1].split(",")).collect(Collectors.toSet()));
                        ctx.writeAndFlush(groupCreateRequestMessage);
                        break;
                    case "gmembers":
                        GroupMembersRequestMessage groupMembersRequestMessage = new GroupMembersRequestMessage(contents[1]);
                        ctx.writeAndFlush(groupMembersRequestMessage);
                        break;
                    case "gjoin":
                        GroupJoinRequestMessage groupJoinRequestMessage = new GroupJoinRequestMessage(username, contents[1]);
                        ctx.writeAndFlush(groupJoinRequestMessage);
                        break;
                    case "gquit":
                        GroupQuitRequestMessage groupQuitRequestMessage = new GroupQuitRequestMessage(username, contents[1]);
                        ctx.writeAndFlush(groupQuitRequestMessage);
                        break;
                    case "quit":
                        ctx.channel().close();
                        Client.POOL.shutdownNow();
                        return;
                    default:
                        break;
                }
            }
        });

        // 连接事件传递下去
        ctx.fireChannelActive();
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
            return;
        }
        log.info("receive message : {}", msg.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接被关闭");
    }
}
