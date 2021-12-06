package com.hikvision.producer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hikvision.model.entity.BrokerMessageLog;

/**
 *
 * @author zhangwei151
 * @date 2021/12/3 14:05
 */

public interface BrokerMessageLogDOMapper extends BaseMapper<BrokerMessageLog> {
    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessageLog record);

    int insertSelective(BrokerMessageLog record);

    BrokerMessageLog selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessageLog record);

    int updateByPrimaryKey(BrokerMessageLog record);
}