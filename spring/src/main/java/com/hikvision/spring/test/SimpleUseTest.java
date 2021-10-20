package com.hikvision.spring.test;

import com.hikvision.spring.model.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.ActiveProfiles;

/**
 * <p>
 *     该测试用例中只简单测试spring的使用
 * </p>
 *
 * @author zhangwei151
 * @description SimpleUseTest
 * @date 2021/7/28 16:13
 */

@ActiveProfiles("dev")
public class SimpleUseTest {

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
    public void testUseBean() {
        final UserDto bean = applicationContext.getBean(UserDto.class);
        System.out.println(bean);
        final Object userDto = applicationContext.getBean("userDto");
        System.out.println(userDto);
        System.out.println(bean == userDto);
    }
}
