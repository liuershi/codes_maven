package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.model.DoorDto;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @author zhangwei151
 * @description 测试spring和mybatis整合使用
 * @date 2021/8/8 15:35
 */
public class Test4 {

    /**
     * 整合后同个mapper多次调用相同方法不是一个会话，不共享一级缓存
     */
    @Test
    public void test1() {
        ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext("springApplication.xml");

        // 手动开启事务可以保证其在一个会话当中，此时共享一级缓存
        DataSourceTransactionManager txManager = (DataSourceTransactionManager) cpx.getBean("txManager");
        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());

        DoorDao doorDao = cpx.getBean(DoorDao.class);
        List<DoorDto> doorDtos = doorDao.listAll();
        doorDtos.forEach(System.out::println);
        List<DoorDto> doorDtos1 = doorDao.listAll();
        System.out.println(doorDtos==doorDtos1);
    }
}
