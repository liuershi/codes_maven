package com.hikvision.mybatis.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangwei151
 * @description HistoryPersonReq
 * @date 2021/8/26 18:30
 */
@Data
@Builder
public class HistoryPersonReq {
    private Integer pageNo;
    private Integer pageSize;
    private LocalDateTime inStartTime;

    private LocalDateTime inEndTime;
    private LocalDateTime outStartTime;

    private LocalDateTime outEndTime;

    private String personName;
}
