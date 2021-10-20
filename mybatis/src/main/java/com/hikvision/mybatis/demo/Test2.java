package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei151
 * @description 直接创建SimpleExecutor
 * @date 2021/7/27 23:28
 */
public class Test2 {

    private Configuration configuration;
    private Transaction transaction;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException, SQLException {
        final InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        configuration = sqlSessionFactory.getConfiguration();
        final Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.13.13:5432/postgres?useUnicode=true", "postgres", "password");
        transaction = new JdbcTransaction(connection);
    }

    @Test
    public void testSimpleExecutor() throws SQLException {
        final SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, transaction);
        final MappedStatement ms = configuration.getMappedStatement("com.hikvision.mybatis.dao.DoorDao.listAll");
        final List<DoorDto> result = simpleExecutor.doQuery(ms, null, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(null));
        // 使用simpleExecutor时，对于相同sql语句，不会重用预编译的statement，所以效率不是很
        simpleExecutor.doQuery(ms, null, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(null));
        result.forEach(System.out::println);
    }

    /**
     * 使用BatchExecutor演示预编译重用statement
     */
    @Test
    public void testBatchExecutor() throws SQLException {
        BatchExecutor batchExecutor = new BatchExecutor(configuration, transaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.hikvision.mybatis.dao.DoorDao.listAll");
        List<DoorDto> result = batchExecutor.doQuery(mappedStatement, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(null));
        batchExecutor.doQuery(mappedStatement, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(null));
        result.forEach(System.out::println);
        /**
         * 注意：BatchExecutor的MapperStatement的重用只针对增删改操作，对于查操作而言，BatchExecutor和SimpleExecutor没有任何区别
         */
        MappedStatement ms = configuration.getMappedStatement("com.hikvision.mybatis.dao.DoorDao.updateNameById");
        Map<String, String> params = new HashMap<String, String>(){{
            put("name", "法外狂徒张三的门");
            put("id", "111");
        }};
        int updateOne = batchExecutor.doUpdate(ms, params);
        int updateTwo = batchExecutor.doUpdate(ms, params);
        System.out.println("two result is = " + updateOne + " " + updateTwo);
        // 此时会发现并未修改成功，这是由于mybatis的批处理需要手动刷新，不调用刷新方法时只会设置参数，并不会提交事务，如下
        List<BatchResult> batchResults = batchExecutor.flushStatements();
        System.out.println(batchResults);
    }

    /**
     * 演示基础执行器的使用：BaseExecutor
     *      说明：由于SimpleExecutor、ResumeExecutor、BatchExecutor等三种执行器都包含共同操作，比如获取连接、关闭连接、维护缓存等等，
     *      所以最好的实现就是抽出一个公共类，封装这些公共操作，所以BaseExecutor就是我们说的公共类
     */
    @Test
    public void testUseBaseExecutor() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, transaction);
        // 具体执行器的操作都是doQuery和doUpdate，而BaseExecutor抽象出来的操作是query和update等
        MappedStatement ms = configuration.getMappedStatement("com.hikvision.mybatis.dao.DoorDao.listAll");
        List<DoorDto> result = executor.query(ms, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        // 可以看见，就算使用SimpleExecutor执行两次相同的Sql还是未执行两次语句，这是由于BaseExecutor中的一级缓存起作用了
        executor.query(ms, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        result.forEach(System.out::println);
    }

    /**
     * 测试二级缓存的使用
     */
    @Test
    public void testCacheExecutor() throws SQLException {
        Executor simpleExecutor = new SimpleExecutor(configuration, transaction);
        Executor executor = new CachingExecutor(simpleExecutor);
        MappedStatement ms = configuration.getMappedStatement("com.hikvision.mybatis.dao.DoorDao.listAll");
        executor.query(ms, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        // 必须先提交事务，否则无法后续相同请求无法查询到缓冲
        /**
         * 提交事务的原因：
         *      实际上在query时并不会先将结果缓存到Cache对象中，而是先缓存到map中，在提交事务后，
         *      会便利map对象然后真正缓存到Cache中，所以需要提交事务后面的查询才可见缓存（具体参照源码）
         */
        executor.commit(true);
        executor.query(ms, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        executor.query(ms, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    }

    /**
     * 测试直接使用Sqlsession，相对于使用Executor对象，结果都是一样的，只是Sqlsession封装了操作，相对来说更简单
     */
    @Test
    public void testSqlsession() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<DoorDto> objects = sqlSession.selectList("com.hikvision.mybatis.dao.DoorDao.listAll");
        objects.forEach(System.out::println);
        // 默认的CachingExecutor中的代理执行器的类型为SimpleExecutor，可使用重载的openSession方法获取指定类型的执行器
//        sqlSessionFactory.openSession(ExecutorType.REUSE);
    }
}
