package com.hikvision.producer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangwei151
 * @date 2021/12/3 14:30
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {
    /**
     * 发送中
     */
    ORDER_SENDING("0"),
    /**
     * 成功
     */
    ORDER_SEND_SUCCESS ("1"),
    /**
     * 失败
     */
    ORDER_SEND_FAILURE ("2");

    final private String code;
}
