package com.hikviision.netty.four.rpc.handler;

import com.hikviision.netty.four.RpcResponseMessage;
import com.hikviision.netty.four.rpc.RpcClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:46
 */
@ChannelHandler.Sharable
@Slf4j
public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    /**
     * 存放操作计数与其对于的promise结果的映射,
     *
     * 注意:由于当前handler使用的@Sharable注解,而且存在map属于状态的保存,需要自己保证线程安全.不过在当前例子中
     * 使用了现场安全的map实现,也未包含非原子操作,所以是线程安全的.
     */
    public static Map<Integer, Promise<Object>> map = new ConcurrentHashMap<>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.info("receive info is : {}", msg.toString());
        int sequenceId = msg.getSequenceId();
        // 移除当前消息对应的promise，防止map中数据越来越多
//        Promise<Object> promise = RpcClient.map.get(sequenceId);
        Promise<Object> promise = map.remove(sequenceId);
        if (promise != null) {
            Exception exception = msg.getExceptionValue();
            if (exception != null) {
                promise.setFailure(exception);
            } else {
                promise.setSuccess(msg.getReturnValue());
            }
        }
    }
}
