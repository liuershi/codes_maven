package com.hikvision.mybatis.demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwei151
 * @description Test9
 * @date 2021/9/4 21:30
 */
public class Test9 {

    private SqlSessionFactory factory;
    private SqlSession session;

    @Before
    public void init() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        session = factory.openSession();
    }

    @After
    public void close() {
        session.close();
    }


    /**
     * mybatis处理结果集的过程为：
     *      ResultSetHandler ==> ResultContext ==> ResultHandler
     *      说明：
     *      ResultSetHandler会循环获取ResultSet，每次获取后拿到当前游标对应的行数据，
     *      将数据交给ResultContext，通过其将行数据转换为对应的Java对象，然后在将Java对象
     *      放入ResultHandler保存，看源码也可发现ResultHandler中有List进出存储的。
     *
     *      注意：
     *      中间加上ResultContext的作用，为什么要加上ResultContext呢，为什么不直接将数据转换
     *      成对象放入ResultHandler保存呢，这是由于在实际开发中，可能存在循环获取数据时，获取到
     *      某行想要的数据就不处理了，后面的就不需要了，而通过ResultContext的stop()可以实现停止
     *      数据保存到ResultHandler，从而控制数据量的获取。
     */
    @Test
    public void test1() {
        List<Object> result = new ArrayList<>();

        ResultHandler resultHandler = new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
                if (resultContext.getResultCount() > 1) {
                    resultContext.stop();
                    return;
                }
                result.add(resultContext.getResultObject());
            }
        };
        // 演示通过ResultContext控制数据量的获取
        session.select("com.hikvision.mybatis.dao.TbUserMapper.listAll", resultHandler);

        System.out.println("size = " + result.size());
    }
}
