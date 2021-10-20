package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.BlogMapper;
import com.hikvision.mybatis.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangwei151
 * @description Test10
 * @date 2021/9/20 21:58
 */
public class Test10 {

    private SqlSession session;
    private BlogMapper mapper;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        session = sqlSessionFactoryBuilder.build(inputStream).openSession();
        mapper = session.getMapper(BlogMapper.class);
    }

    @After
    public void destroy() {
        session.close();
    }

    @Test
    public void test1() {
        Blog byId = mapper.getById("1");
        System.out.println(byId);
    }
}
