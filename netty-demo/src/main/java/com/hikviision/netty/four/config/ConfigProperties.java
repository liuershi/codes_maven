package com.hikviision.netty.four.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhangwei151
 * @date 2021/12/12 19:15
 */
@Slf4j
public class ConfigProperties {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigProperties.class.getResourceAsStream("/application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            log.error("加载配置文件错误", e);
        }
    }

    public static String getValue(String key) {
        return PROPERTIES.getProperty(key);
    }
}
