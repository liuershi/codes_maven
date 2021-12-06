package com.hikviision.netty.utils;

import java.io.*;

/**
 * @author zhangwei151
 * @date 2021/12/5 16:06
 */
public class ByteUtils {

    /**
     * 对象序列化成字节数组，使用JDK的序列化方式
     * @param object
     * @return
     */
    public static byte[] serializer(Object object) {
        if (object == null) {
            return new byte[]{};
        }
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bao)) {
            oos.writeObject(object);
            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    /**
     * jdk序列化算法反序列化
     * @param content
     * @return
     */
    public static Object deserializer(byte[] content) {
        if (content == null || content.length == 0) {
            return content;
        }
        try (ByteArrayInputStream bai = new ByteArrayInputStream(content);
             ObjectInputStream ois = new ObjectInputStream(bai)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
