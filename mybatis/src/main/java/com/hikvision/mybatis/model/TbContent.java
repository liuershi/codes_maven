package com.hikvision.mybatis.model;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @description TbContent
 * @author zhangwei151
 * @date 2021/9/20 22:09
 */

@Data
@AllArgsConstructor
@ToString
public class TbContent {
    private String id;

    private String conent;

    private String userId;

    private LocalDateTime time;
}