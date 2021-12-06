package com.hikvision.producer;

import com.hikvision.model.entity.Order;
import com.hikvision.model.utils.IdUtil;
import com.hikvision.producer.config.DruidPropertiesConfig;
import com.hikvision.producer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author zhangwei151
 * @date 2021/12/3 15:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootProducerApplicationTests {

    @Autowired
    private OrderService service;

    @Autowired
    private DruidPropertiesConfig propertiesConfig;

    private final IdUtil idUtil = new IdUtil(0 , 1);

    @Test
    public void testSend() {
        Order order = new Order();
        order.setId(idUtil.nextId());
        order.setName("测试订单");
        order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
        service.createOrder(order);
        System.out.println(propertiesConfig.getMaxActive());
    }
}
