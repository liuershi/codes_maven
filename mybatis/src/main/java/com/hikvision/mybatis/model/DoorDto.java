package com.hikvision.mybatis.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @description DoorDto
 * @author zhangwei151
 * @date 2021/7/23 10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoorDto implements Serializable {
    /**
    * 主键id
    */
    private String acsDoorId;

    /**
    * 门禁点id
    */
    private String doorIndexCode;

    /**
    * 门禁点名称
    */
    private String doorName;

    /**
    * 门禁设备唯一标识，多个值用,隔开
    */
    private String acsDevIndexCode;

    /**
    * 区域唯一标示
    */
    private String regionIndexCode;

    /**
    * 区域名称
    */
    private String regionName;

    /**
    * 门禁点序号
    */
    private String doorNo;

    /**
    * 设备序列号
    */
    private String channelNo;

    /**
    * 通道类型
    */
    private String channelType;

    /**
    * 安装位置
    */
    private String installLocation;

    /**
    * 备注
    */
    private String remark;

    /**
    * 删除状态（0：正常；-1：删除）
    */
    private Integer status;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;
}