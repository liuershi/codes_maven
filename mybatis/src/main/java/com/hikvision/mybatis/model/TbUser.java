package com.hikvision.mybatis.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @description TbUser
 * @author zhangwei151
 * @date 2021/8/31 22:47
 */

/**
    * 用户表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUser {
    private String id;

    private String userName;

    private Integer age;

    private Double height;

    private String address;

    private Date dateBirth;
}