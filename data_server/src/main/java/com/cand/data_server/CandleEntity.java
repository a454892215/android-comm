package com.cand.data_server;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "candle")
public class CandleEntity {

    @Id
    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime date; // 使用日期作为主键

    @Column(name = "close", nullable = false, precision = 36, scale = 16)
    private BigDecimal close;

    @Column(name = "open", nullable = false, precision = 36, scale = 16)
    private BigDecimal open;

    @Column(name = "max", nullable = false, precision = 36, scale = 16)
    private BigDecimal max;

    @Column(name = "min", nullable = false, precision = 36, scale = 16)
    private BigDecimal min;
    @Column(name = "volume", nullable = false, precision = 22, scale = 2)
    private BigDecimal volume;

    // Getters and Setters
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }


    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

}
