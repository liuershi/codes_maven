package com.hikvision.model.utils;

/**
 * 使用雪花算法生成订单ID
 *
 * @author zhangwei151
 * @date 2021/12/3 16:12
 */
public class IdUtil {
    /**
     * 起始的时间戳
     */
    private final static long START_TIME = 1635327897000L;

    /**
     * 序列号位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心位数
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE_NUM = ~(-1L << SEQUENCE_BIT);

    /**
     * 每部分向左移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 数据中心
     */
    private long datacenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;

    public IdUtil(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATA_CENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成唯一ID
     * @return
     */
    public synchronized long nextId() {
        long newTime = getNewTime();
        if (newTime < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (newTime == lastStmp) {
            // 相同毫秒时间内自增
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            // 溢出时
            if (sequence == 0) {
                newTime = getNextMill();
            }
        } else {
            // 不同毫秒，序列号为0
            sequence = 0;
        }

        lastStmp = newTime;

        return (newTime - START_TIME) << TIMESTAMP_LEFT
                | datacenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNewTime() {
        return System.currentTimeMillis();
    }

    private long getNextMill() {
        long mill = getNewTime();
        while (mill <= lastStmp) {
            mill = getNewTime();
        }
        return mill;
    }
}
