package com.hikvision.mybatis.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @description TbEvent
 * @author zhangwei151
 * @date 2021/8/28 17:42
 */

/**
    * 事件表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEvent {
    /**
    * 主键
    */
    private String eventId;

    /**
    * 事件类型
    */
    private Integer eventType;

    /**
    * 本地事件产生时间
    */
    private Date eventTime;

    /**
    * 事件名称
    */
    private String eventName;

    /**
    * 事件代码
    */
    private Integer eventCode;

    /**
    * 卡号
    */
    private String eventCard;

    /**
    * 人员ID
    */
    private String personId;

    /**
    * 人员姓名
    */
    private String personName;

    /**
    * 部门ID
    */
    private String deptId;

    /**
    * 部门路径
    */
    private String deptPath;

    /**
    * 部门路径名称
    */
    private String deptName;

    /**
    * 产生事件的设备id
    */
    private String deviceId;

    /**
    * 设备code
    */
    private String deviceCode;

    /**
    * 产生事件的门禁点编码
    */
    private String doorIndexCode;

    /**
    * 产生事件的设备名称
    */
    private String deviceName;

    /**
    * 产生事件的设备类型
    */
    private Integer deviceType;

    /**
    * 门名称
    */
    private String doorName;

    /**
    * 一级设备id
    */
    private String devicel1Id;

    /**
    * 一级设备名称
    */
    private String devicel1Name;

    /**
    * 一级设备类型
    */
    private Integer devicel1Type;

    /**
    * 二级设备id
    */
    private String devicel2Id;

    /**
    * 二级设备名称
    */
    private String devicel2Name;

    /**
    * 二级设备类型
    */
    private Integer devicel2Type;

    /**
    * 三级设备id
    */
    private String devicel3Id;

    /**
    * 三级设备名称
    */
    private String devicel3Name;

    /**
    * 自定义标识
    */
    private String tag;

    /**
    * 控制器所在区域
    */
    private String regionIdDevice;

    /**
    * 门禁点所在区域
    */
    private String regionIdDoor;

    /**
    * 门禁点所在区域名称
    */
    private String doorRegionName;

    /**
    * 控制器所在区域名称
    */
    private String deviceRegionName;

    /**
    * 三级设备类型
    */
    private Integer devicel3Type;

    /**
    * 门id
    */
    private String doorId;

    /**
    * 关联录像
    */
    private String triggerRecord;

    /**
    * 图片url
    */
    private String remark;

    /**
    * 身份证图片url
    */
    private String cardPic;

    /**
    * 卡状态。1：正常卡；2：挂失卡
    */
    private Integer cardStates;

    /**
    * 本地平台接受事件的时间
    */
    private Date receiveTime;

    /**
    * 图片服务的serviceNodes
    */
    private String svrIndexCode;

    /**
    * 工号
    */
    private String jobNumber;

    /**
    * 身份证号
    */
    private String certNo;

    /**
    * 学工号
    */
    private String studentId;

    /**
    * 出入。0：出；1：入；-1：未知
    */
    private String inAndOut;

    /**
    * 事件产生时差
    */
    private String eventTimeDif;

    /**
    * UTC平台接受事件的时间
    */
    private Long utcReceiveTime;

    /**
    * 平台接受的事件时差
    */
    private String receiveTimeDif;

    /**
    * 扩展字段
    */
    private Object extendProperty;
}