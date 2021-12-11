package com.hikviision.netty.four;

import lombok.Data;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:29 下午
 * @Version 1.0
 */
@Data
public abstract class AbstractResponseMessage extends Message {
    public boolean success;
    private String reason;

    public AbstractResponseMessage(){}

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}