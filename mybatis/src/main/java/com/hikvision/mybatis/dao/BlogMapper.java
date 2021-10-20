package com.hikvision.mybatis.dao;

import com.hikvision.mybatis.model.Blog;

/**
 * @author zhangwei151
 * @description BlogMapper
 * @date 2021/9/20 21:51
 */
public interface BlogMapper {

    Blog getById(String id);
}
