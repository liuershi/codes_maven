package com.hikviision.netty.four.rpc.handler;

import com.hikviision.netty.four.RpcRequestMessage;
import com.hikviision.netty.four.RpcResponseMessage;
import com.hikviision.netty.four.rpc.service.HelloService;
import com.hikviision.netty.four.rpc.service.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:18
 */
@ChannelHandler.Sharable
@Slf4j
public class RequestHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) {

        RpcResponseMessage responseMessage = new RpcResponseMessage();
        responseMessage.setSequenceId(msg.getSequenceId());

        try {
            // 通过反射调用接口方法
            log.info("request msg : {}", msg.toString());
            Class<?> clazz = Class.forName(msg.getInterfaceName());
            Object instance = ServiceFactory.getInstance(clazz);
            if (instance instanceof HelloService) {
                HelloService helloService = (HelloService) instance;
                Method method = helloService.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
                Object invoke = method.invoke(helloService, msg.getParameterValue());
                responseMessage.setReturnValue(invoke);
            }
        } catch (Exception e) {
            responseMessage.setExceptionValue(e);
        }

        // 返回响应给客户端
        ctx.writeAndFlush(responseMessage);
    }
}
