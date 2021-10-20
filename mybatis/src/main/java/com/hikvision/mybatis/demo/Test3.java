package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhangwei151
 * @description 探究mybatis的一级缓存
 * @date 2021/8/7 15:02
 */
public class Test3 {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory  = sqlSessionFactoryBuilder.build(inputStream);
    }

    @Test
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DoorDao mapper = sqlSession.getMapper(DoorDao.class);
        List<DoorDto> result1 = mapper.listAll();
        List<DoorDto> result2 = mapper.listAll();
        System.out.println(result1==result2);

        // 一级缓存是Sqlsession级别的，即会话级别的，不同会话缓存不同
        List<DoorDto> result3 = sqlSessionFactory.openSession().getMapper(DoorDao.class).listAll();
        System.out.println(result1 == result3);

        // 相同的sql情况下，如果statement不一样也无法命中缓存
        List<DoorDto> result4 = mapper.listAllTwo();
        System.out.println(result1 == result4);

        // 参数不一样也无法命中缓存
    }

    @Test
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DoorDao mapper = sqlSession.getMapper(DoorDao.class);
        List<DoorDto> result1 = mapper.listAllTwo();
        // 清空缓冲
        sqlSession.clearCache();
        List<DoorDto> result2 = mapper.listAllTwo();
        System.out.println(result1==result2);
    }

    @Test
    public void test3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DoorDao mapper = sqlSession.getMapper(DoorDao.class);
        // 在配置文件中设置localCacheScope为STATEMENT来禁用一级缓存
        mapper.listAll();
        List<DoorDto> doorDtos = mapper.listAll();
        doorDtos.forEach(System.out::println);
    }
}
