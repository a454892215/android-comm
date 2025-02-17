package com.cand.data_base;

import com.cand.util.DateU;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;

/**
 * 因为这是很多币的通用表， 不同的币表名不一样，所以表名不能使用@Table注解写死，需要自行定义
 */
@SuppressWarnings("unused")
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

    /**
     * {"arg":{"channel":"trades","instId":"SOL-USDT-SWAP"},"data":[{"instId":"SOL-USDT-SWAP","tradeId":"620869571","px":"187.08","sz":"6.28","side":"sell","ts":"1736909125942","count":"7"}]}
     */
    public static CandleEntity createSimpleObj() {

        CandleEntity candleEntity = new CandleEntity();
        // candleEntity.setLongTimestamp(1736909125942L);
        candleEntity.setLongTimestamp(System.currentTimeMillis());
        candleEntity.setClose(BigDecimal.valueOf(187.08));
        candleEntity.setOpen(BigDecimal.valueOf(187.08));
        candleEntity.setHigh(BigDecimal.valueOf(187.08));
        candleEntity.setLow(BigDecimal.valueOf(187.08));
        candleEntity.setVolume(BigDecimal.valueOf(30000.23));
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

    @Column(name = "volume", nullable = false, precision = 36, scale = 16)
    private BigDecimal volume;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getLongTimestamp() {
        return timestamp.atZone(ZoneOffset.UTC).toInstant().toEpochMilli(); // 毫秒级时间戳
    }

    public void setLongTimestamp(long timestamp) {
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "CandleEntity{" +
                "timestamp=" + DateU.getBeiJingTime(timestamp) +
                ", close=" + close.toPlainString() +
                ", open=" + open.toPlainString() +
                ", high=" + high.toPlainString() +
                ", low=" + low.toPlainString() +
                ", volume=" + volume.toPlainString() +
                '}';
    }

}
