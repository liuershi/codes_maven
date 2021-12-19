package com.hikviision.netty.four.rpc;

import com.hikviision.netty.four.RpcRequestMessage;
import com.hikviision.netty.four.handler.MessageCodec;
import com.hikviision.netty.four.rpc.handler.ResponseHandler;
import com.hikviision.netty.four.rpc.service.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:41
 */
@Slf4j
public class RpcClient {
    public static Channel channel;

    private final static Object LOCK = new Object();

    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    private static void initChannel() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            final MessageCodec SHARED_CODEC_HANDLER = new MessageCodec();
            final ResponseHandler SHARED_RESPONSE_HANDLER = new ResponseHandler();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 11, 4, 1, 0));
                            pipeline.addLast("loggingHandle", new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast("messageCodec", SHARED_CODEC_HANDLER);
                            pipeline.addLast("responseHandler", SHARED_RESPONSE_HANDLER);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 9999)).sync();

            channel = channelFuture.channel();
            channelFuture.channel().closeFuture().addListener(promise -> {
                eventLoopGroup.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 这种方式较麻烦，没有屏蔽底层细节，通过代理类的方式方便使用
        /*RpcRequestMessage message = new RpcRequestMessage("com.hikviision.netty.four.rpc.service.HelloService",
                "sayHello", String.class, new Class[]{String.class}, new Object[]{"李四"});*/
        HelloService proxyService = getProxyService(HelloService.class);
        String s1 = proxyService.sayHello("王五");
        System.out.println(s1);
        String s2 = proxyService.sayHello("赵六");
        System.out.println(s2);
    }


    public static <T> T getProxyService(Class<T> service) {
        // 使用JDK的代理实现
        return  (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, (proxy, method, args) -> {
            // 1.将方法参数转换为统一的RpcRequestMessage对象
            Integer nextId = SequenceFactory.getNextId();
            RpcRequestMessage requestMessage = new RpcRequestMessage(
                    nextId,
                    service.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            // 2.获取channel并将数据写出
            Channel channel = getChannel();
            channel.writeAndFlush(requestMessage);
            // 3.创建promise对象，参数为指定异步接收处理结果的线程池
            DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
            // 阻塞等待请求结束
            ResponseHandler.map.put(nextId, promise);
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                throw new RuntimeException(promise.cause());
            }
        });
    }
}