package com.learn.demo.mall.goods.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 雪花算法工具类
 * @author zh_cr
 */
@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeIdWorker {
    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    private final static long TW_EPOCH = 1288834974657L;
    // 机器标识位数
    private final static long WORKER_ID_BITS = 5L;
    // 数据中心标识位数
    private final static long DATACENTER_ID_BITS = 5L;
    // 机器ID最大值
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    // 数据中心ID最大值
    private final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    // 毫秒内自增位
    private final static long SEQUENCE_BITS = 12L;
    // 机器ID偏左移12位
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心ID左移17位
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    // 时间毫秒左移22位
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    // 上次生产id时间戳
    private static long lastTimestamp = -1L;
    // 0，并发控制
    private long sequence = 0L;

    private long workerId;

    private long datacenterId;

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID

        return ((timestamp - TW_EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}