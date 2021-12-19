package com.hikviision.netty.four.protocol;

import com.google.gson.*;
import com.hikviision.netty.four.rpc.Test;
import com.hikviision.netty.utils.ByteUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 消息的序列化算法
 *
 * @author zhangwei151
 * @date 2021/12/12 18:59
 */
public interface MySerializer {

    String KEY = "netty.algorithm";

    /**
     * 序列化
     * @param object 对象
     * @param <T> 对象类型
     * @return
     */
    <T> byte[] serializer(T object);

    /**
     * 反序列化
     * @param clazz 对象类型
     * @param bytes 字节数组
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化算法枚举
     */
    enum Algorithm implements MySerializer {
        /**
         * jdk序列化
         */
        JAVA {
            @Override
            public <T> byte[] serializer(T object) {
                return ByteUtils.serializer(object);
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Object deserializer = ByteUtils.deserializer(bytes);
                return (T) deserializer;
            }
        },

        /**
         * JSON序列化
         */
        JSON {
            @Override
            public <T> byte[] serializer(T object) {
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(Class.class, new ClassCodec())
                        .create();
                String json = gson.toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(Class.class, new ClassCodec())
                        .create();
                String content = new String(bytes, StandardCharsets.UTF_8);
                return gson.fromJson(content, clazz);
            }

            class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

                @SneakyThrows
                @Override
                public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    String asString = json.getAsString();
                    return Class.forName(asString);
                }

                @Override
                public JsonElement serialize(Class src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.getName());
                }
            }
        };
    }
}
