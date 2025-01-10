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
        candleEntity.timestamp = LocalDateTime.now();
        candleEntity.setClose(BigDecimal.valueOf(20));
        candleEntity.setOpen(BigDecimal.valueOf(30));
        candleEntity.setHigh(BigDecimal.valueOf(59));
        candleEntity.setLow(BigDecimal.valueOf(10));
        candleEntity.setVolume(BigDecimal.valueOf(30000));
        return candleEntity;
    }

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp; // 使用日期作为主键
    @Column(name = "close", nullable = false, precision = 36, scale = 16)
    private BigDecimal close;
    @Column(name = "open", nullable = false, precision = 36, scale = 16)
    private BigDecimal open;

    @Column(name = "high", nullable = false, precision = 36, scale = 16)
    private BigDecimal high;

    @Column(name = "low", nullable = false, precision = 36, scale = 16)
    private BigDecimal low;

    @Column(name = "volume", nullable = false, precision = 22, scale = 2)
    private BigDecimal volume;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close.stripTrailingZeros();
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open.stripTrailingZeros();
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high.stripTrailingZeros();
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low.stripTrailingZeros();
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume.stripTrailingZeros();
    }

    @Override
    public String toString() {
        return "CandleEntity{" +
                "timestamp=" + timestamp +
                ", close=" + close.toPlainString() +
                ", open=" + open.toPlainString() +
                ", high=" + high.toPlainString() +
                ", low=" + low.toPlainString() +
                ", volume=" + volume.toPlainString() +
                '}';
    }

}
