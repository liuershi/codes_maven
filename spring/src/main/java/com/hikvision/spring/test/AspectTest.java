package com.hikvision.spring.test;

import com.hikvision.spring.service.HelloService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author zhangwei151
 * @description AspectTest
 * @date 2021/10/18 14:31
 */
public class AspectTest {
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

        // 设置profile
        Environment environment = applicationContext.getEnvironment();
        if (environment instanceof StandardEnvironment) {
            StandardEnvironment standardEnvironment = (StandardEnvironment) environment;
            standardEnvironment.setActiveProfiles("development");
        }
        // 重启
        applicationContext.refresh();
    }

    @Test
    public void test1() {
        HelloService service = applicationContext.getBean(HelloService.class);
        service.say("法外狂徒张三");
    }
}
