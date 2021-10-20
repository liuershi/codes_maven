package com.hikvision.mybatis.dao;

import com.hikvision.mybatis.model.HistoryPersonReq;
import com.hikvision.mybatis.model.TbEvent;

import java.util.List;
import java.util.Map;

/**
 *
 * @description TbEventMapper
 * @author zhangwei151
 * @date 2021/8/28 17:42
 */

public interface TbEventMapper {
    TbEvent getById(String id);

    int insert(TbEvent record);

    int insertSelective(TbEvent record);

    List<Map> listHistory(HistoryPersonReq req);

    int getCount(HistoryPersonReq req);
}