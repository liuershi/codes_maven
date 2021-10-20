package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.internal.PAForUserEnc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei151
 * @description 演示缓存具体实现过程
 * @date 2021/8/18 23:08
 */
public class Test5 {

    private Configuration configuration;

    @Before
    public void init() {
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        configuration = sqlSessionFactory.getConfiguration();
    }

    @Test
    public void testCache() {
        Cache cache = configuration.getCache("com.hikvision.mybatis.dao.DoorDao");
        DoorDto build = DoorDto.builder().acsDoorId("1212121").doorNo("12345").build();
        cache.putObject("params", build);

        System.out.println(cache.getObject("params"));
    }
}
