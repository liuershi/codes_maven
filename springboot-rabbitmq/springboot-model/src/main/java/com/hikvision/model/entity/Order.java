package com.hikvision.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangwei151
 * @date 2021/12/3 10:44
 */
@Data
@TableName(value = "t_order")
public class Order implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long id;
    private String name;
    private String messageId;
}
