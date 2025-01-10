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
