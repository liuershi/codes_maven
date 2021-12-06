package com.hikvision.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhangwei151
 * @date 2021/12/3 10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "broker_message_log")
public class BrokerMessageLog {
    /**
     * 消息ID
     */
    @TableId(type = IdType.INPUT)
    private String messageId;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 重试次数
     */
    private Integer tryCount;
    /**
     * 消息状态
     */
    private String status;
    /**
     * 下次重试时间
     */
    private LocalDateTime nextRetry;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
