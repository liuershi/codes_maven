package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhangwei151
 * @description 使用配置文件创建SqlSession执行业务
 * @date 2021/7/23 10:43
 */
public class Test1 {
    public static void main(String[] args) throws IOException {
        /**
         *  方式一：使用配置文件方式创建SqlSessionFactory
         */
        /*InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);*/


        /**
         *  方式二：使用Configuration对象创建SqlSessionFactory
         */
        DataSource dataSource = new PooledDataSource("org.postgresql.Driver",
                "jdbc:postgresql://10.4.67.206:7092/irds_irdsdb?useUnicode=true",
                "irds_irdsdb_user",
                "fbKROjx0");
        JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.getTypeAliasRegistry().registerAlias(DoorDto.class);
        configuration.addMapper(DoorDao.class);
        // 不过这种方式无法将定义在xml文件中的sql语句映射成MappedStatement使用，只能取到使用注解定义的sql
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
//            useSqlMethod(sqlSession);
//            userDaoObject(sqlSession);
//            useUpdateCommand(sqlSession);
            useSelectByAttr(sqlSession);
//            useDoorByLike(sqlSession);
        }
    }

    private static void useDoorByLike(SqlSession sqlSession) {
        DoorDao mapper = sqlSession.getMapper(DoorDao.class);
        final List<DoorDto> result = mapper.getDoorByLike("门", null);
        result.forEach(System.out::println);
    }

    private static void useSelectByAttr(SqlSession sqlSession) {
        final DoorDao mapper = sqlSession.getMapper(DoorDao.class);
        final List<DoorDto> doorNames = mapper.getValueByAttr("door_name", "zwtest_门_1");
        System.out.println("size is = " + doorNames.size());
        final List<DoorDto> regionNames = mapper.getValueByAttr("region_name", "根节点");
        System.out.println("size is = " + regionNames.size());
    }

    private static void useUpdateCommand(SqlSession sqlSession) {
        final DoorDao doorDao = sqlSession.getMapper(DoorDao.class);
        final boolean result;
        try {
            result = doorDao.updateNameById("张三的门", "111");
//                int i = 1 / 0;
            System.out.println(result);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
    }

    /**
     * 这种方式通过sqlsession提供的方法curd方式调用实现，但是存在问题(不推荐)。
     *
     * 诚然，这种方式能够正常工作，对使用旧版本 MyBatis 的用户来说也比较熟悉。
     * 但现在有了一种更简洁的方式——使用和指定语句的参数和返回值相匹配的接口（比如 DoorDao.class），
     * 现在你的代码不仅更清晰，更加类型安全，还不用担心可能出错的字符串字面值以及强制类型转换。
     *
     * @param sqlSession
     */
    private static void useSqlMethod(SqlSession sqlSession) {
        List<DoorDto> result = sqlSession.selectList("com.hikvision.mybatis.dao.DoorDao.listAll");
        result.forEach(System.out::println);
    }

    /**
     * 使用反射的方式获取到dao接口对象。直接调用接口的方法更加安全高效。
     * @param sqlSession
     */
    private static void userDaoObject(SqlSession sqlSession) {
        final DoorDao doorDao = sqlSession.getMapper(DoorDao.class);
        final List<DoorDto> result = doorDao.listAll();
        result.forEach(System.out::println);
    }
}
