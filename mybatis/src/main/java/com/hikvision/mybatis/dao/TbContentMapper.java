package com.hikvision.mybatis.dao;

import com.hikvision.mybatis.model.TbContent;

/**
 *
 * @description TbContentMapper
 * @author zhangwei151
 * @date 2021/9/20 22:09
 */

public interface TbContentMapper {
    int deleteByPrimaryKey(String id);

    int insert(TbContent record);

    int insertSelective(TbContent record);

    TbContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TbContent record);

    int updateByPrimaryKey(TbContent record);
}