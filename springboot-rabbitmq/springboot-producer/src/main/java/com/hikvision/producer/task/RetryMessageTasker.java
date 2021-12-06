package com.hikvision.producer.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hikvision.model.entity.BrokerMessageLog;
import com.hikvision.model.entity.Order;
import com.hikvision.model.utils.JsonUtil;
import com.hikvision.producer.dao.BrokerMessageLogDOMapper;
import com.hikvision.producer.enums.StatusEnum;
import com.hikvision.producer.producer.RabbitmqOrderSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangwei151
 * @date 2021/12/3 14:39
 */
@Component
@Slf4j
public class RetryMessageTasker {

    @Autowired
    private BrokerMessageLogDOMapper brokerMessageLogDOMapper;

    @Autowired
    private RabbitmqOrderSender sender;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void reSend() {
        log.info("-----------定时任务开始-----------");
        List<BrokerMessageLog> logs = brokerMessageLogDOMapper.selectList(new QueryWrapper<BrokerMessageLog>().eq("status", "0").le("next_retry", LocalDateTime.now()));
        logs.forEach(item -> {
            // 重试次数超过三次
            Integer tryCount = item.getTryCount();
            item.setUpdateTime(LocalDateTime.now());
            if (tryCount >= 3) {
                // 修改状态为失败
                item.setStatus(StatusEnum.ORDER_SEND_FAILURE.getCode());
                brokerMessageLogDOMapper.updateById(item);
                return;
            }
            // 否则重试
            item.setTryCount(++tryCount);
            brokerMessageLogDOMapper.updateById(item);

            Order order = JsonUtil.stringToObject(item.getMessage(), Order.class);
            try {
                sender.sendOrder(order);
            } catch (Exception e) {
                log.error("send failed, error message : {}", e.getMessage());
            }
        });
    }
}
