package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
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
 * @description Test6
 * @date 2021/8/24 22:39
 */
public class Test6 {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    /**
     * 要点：
     *      1.必须手动提交事务才会保存缓存
     *      2.可使用useCache=false属性设置关闭对应方法的缓存
     *      3.也还可以设置flushCache="true"属性关闭对应方法缓存
     */
    @Test
    public void testCache() {
        SqlSession session1 = sqlSessionFactory.openSession();
        DoorDao mapper1 = session1.getMapper(DoorDao.class);
        List<DoorDto> doorDtos = mapper1.listAll();
        mapper1.getDoorById("111");
        // 必须提交事务之后才会将结果保存进二级缓存
        session1.commit();

        SqlSession session2 = sqlSessionFactory.openSession();
        DoorDao mapper2 = session2.getMapper(DoorDao.class);
        mapper2.getDoorById("111");
        List<DoorDto> doorDtos1 = mapper2.listAll();

        // 不可能相等，因为二级缓存会序列化对象
        System.out.println(doorDtos==doorDtos1);
    }

    @Test
    public void testCache2() {
        SqlSession session1 = sqlSessionFactory.openSession();
        DoorDao mapper1 = session1.getMapper(DoorDao.class);
        mapper1.getDoorById("111");
        // 必须提交事务之后才会将结果保存进二级缓存
        session1.commit();

        SqlSession session2 = sqlSessionFactory.openSession();
        DoorDao mapper2 = session2.getMapper(DoorDao.class);
        mapper2.getDoorById("111");
    }
}
