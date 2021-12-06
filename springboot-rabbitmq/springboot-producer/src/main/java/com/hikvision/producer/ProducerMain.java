package com.hikvision.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangwei151
 * @date 2021/12/3 10:49
 */
@SpringBootApplication
@MapperScan(basePackages = "com.hikvision.producer.dao")
public class ProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class, args);
    }
}
