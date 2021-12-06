package com.hikvision.producer.service;

import com.hikvision.model.entity.Order;

/**
 * @author zhangwei151
 * @date 2021/12/3 16:02
 */
public interface OrderService {
    void createOrder(Order order);
}
