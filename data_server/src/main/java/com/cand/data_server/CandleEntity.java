package com.cand.data_server;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * 因为这是很多币的通用表， 不同的币表名不一样，所以表名不能使用@Table注解写死，需要自行定义
 */
@Entity
@Table(name = "test")
public class CandleEntity {
    public CandleEntity() {
        // 无参构造函数，JPA要求
    }

    public CandleEntity(LocalDateTime timestamp, BigDecimal close, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal volume) {
        this.timestamp = timestamp;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public static CandleEntity createSimpleObj() {
        CandleEntity candleEntity = new CandleEntity();
        candleEntity.close = BigDecimal.valueOf(20);
        candleEntity.open = BigDecimal.valueOf(30);
        candleEntity.high = BigDecimal.valueOf(59);
        candleEntity.low = BigDecimal.valueOf(10);
        candleEntity.volume = BigDecimal.valueOf(30000);
        return candleEntity;
    }

    @Id
    @Column(name = "date", nullable = false, updatable = false)
    public LocalDateTime timestamp; // 使用日期作为主键
    @Column(name = "close", nullable = false, precision = 36, scale = 16)
    public BigDecimal close;

    @Column(name = "open", nullable = false, precision = 36, scale = 16)
    public BigDecimal open;

    @Column(name = "high", nullable = false, precision = 36, scale = 16)
    public BigDecimal high;

    @Column(name = "low", nullable = false, precision = 36, scale = 16)
    public BigDecimal low;

    @Column(name = "volume", nullable = false, precision = 22, scale = 2)
    public BigDecimal volume;

}
