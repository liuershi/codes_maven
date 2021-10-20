package com.hikvision.mybatis.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangwei151
 * @description Content
 * @date 2021/9/20 21:42
 */
@Data
public class Content {
    private String id;
    private LocalDateTime time;
    private String content;
    private TbUser user;
}
