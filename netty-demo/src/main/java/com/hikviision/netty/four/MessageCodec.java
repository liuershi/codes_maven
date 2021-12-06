package com.hikviision.netty.four;

import com.hikviision.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangwei151
 * @date 2021/12/5 1:14
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 1.设置魔数，默认AABB（4字节）
        out.writeInt(0xAABB);
        // 2.版本号（1字节）
        out.writeByte(1);
        // 3.序列化算法，约定0：jdk;1：json（1字节）
        out.writeByte(0);
        // 4.指令类型（1字节）
        out.writeByte(msg.getType());
        // 5.请求序号（4字节）
        out.writeInt(msg.getSequenceId());
        // 6.正文长度（4字节）
        byte[] content = ByteUtils.serializer(msg);
        out.writeInt(content.length);
        // 插入一个字节，保证请求头长度为2的次方，为了填充对齐，在这即加上一字节为16（1字节）
        out.writeByte(0xff);
        // 7.数据内容
        out.writeBytes(content);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 解码，按照编码的顺序解码
        // 1.魔数
        int magicNum = in.readInt();
        // 2.版本号
        byte version = in.readByte();
        // 3.序列化算法
        byte serializer = in.readByte();
        // 4.指令类型
        byte operationType = in.readByte();
        // 5.请求序号
        int sequenceId = in.readInt();
        // 6.正文长度
        int length = in.readInt();
        // 7.数据内容
        in.readByte();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Message content = null;
        if (serializer == 0) {
            // 序列化算法为JDK时
             content = (Message) ByteUtils.deserializer(bytes);
        } else {
            // todo
        }
        log.info("解码完成，属性分别如下");
        log.info("魔数：{}; 版本号:{}；序列化算法：{}：指令类型：{}；请求序列：{}；正位长度：{}；正文内容：{}",
                magicNum, version, serializer, operationType, sequenceId, length, content);

        // 传递下去
        out.add(content);
    }
}
