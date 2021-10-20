package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.TbUserMapper;
import com.hikvision.mybatis.model.TbUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangwei151
 * @description 演示mybatis解析参数到sql语句的过程
 * @date 2021/8/31 22:55
 */
public class Test8 {

    private SqlSession session;
    private TbUserMapper mapper;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        session = sqlSessionFactoryBuilder.build(inputStream).openSession();
        mapper = session.getMapper(TbUserMapper.class);
    }

    @After
    public void destroy() {
        session.close();
    }


    /**
     * 演示单参数情况时的解析过程（参照ParamNameResolver#getNamedParams()方法）
     */
    @Test
    public void test1() {
       mapper.getUserById("1");
    }

    /**
     * 单参数带@Param注解时：
     */
    @Test
    public void test2() {
        mapper.getUserByIdTwo("211");
    }

    /**
     * 多参数时
     */
    @Test
    public void test3() {
        mapper.getUserByNameAndAge(25, "张三");
    }

    /**
     * 多餐带注解：
     *       目前mybatis43.4.6版本带不带注解效果好像都一样
     */
    @Test
    public void test4() {
        mapper.getUserByNameAndAgeTwo("张三", 25);
    }

    @Test
    public void test5() {
//        TbUser user = Mockito.mock(TbUser.class);
//        user.setAge(25);
        TbUser user = TbUser.builder().age(25).build();
        mapper.getUserByNameAndAgeThree("张三", user);
    }
}
