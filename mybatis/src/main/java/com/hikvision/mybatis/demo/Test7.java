package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.dao.DoorDao;
import com.hikvision.mybatis.dao.TbEventMapper;
import com.hikvision.mybatis.model.HistoryPersonReq;
import com.hikvision.mybatis.model.TbEvent;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei151
 * @description Test7
 * @date 2021/8/28 17:45
 */
public class Test7 {
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

    @Test
    public void testCustomizeSql() {
        LocalDateTime now = LocalDateTime.now();
        HistoryPersonReq req = HistoryPersonReq.builder().pageNo(1).pageSize(0)
                .inStartTime(now.minusMonths(1))
                .inEndTime(now)
                .outStartTime(now)
                .outEndTime(now.plusDays(2)).build();

        SqlSession session = sqlSessionFactory.openSession();
        TbEventMapper mapper = session.getMapper(TbEventMapper.class);
        List<Map> maps = mapper.listHistory(req);
        System.out.println(maps);

//
//        int count = mapper.getCount(req);
//        System.out.println(count);
    }
}
