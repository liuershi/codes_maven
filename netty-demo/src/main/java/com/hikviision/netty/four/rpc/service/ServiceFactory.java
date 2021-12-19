package com.hikviision.netty.four.rpc.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将类与其实体对象映射关系保存在内存中
 *
 * @author zhangwei151
 * @date 2021/12/18 16:24
 */
public class ServiceFactory {
    public static Properties properties;
    private final static Map<Class<?>, Object> MAP = new ConcurrentHashMap<>();

    static {
        try (InputStream is = ServiceFactory.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(is);

            properties.stringPropertyNames().stream().filter(serviceName -> serviceName.endsWith("Service"))
                    .forEach(service -> {
                        try {
                            Class<?> interfaceClass = Class.forName(service);
                            Class<?> instanceClass = Class.forName(properties.getProperty(service));
                            Object instance = instanceClass.newInstance();
                            MAP.put(interfaceClass, instance);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 获取接口对应的实现类
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object getInstance(Class<T> clazz) {
        return MAP.get(clazz);
    }
}
