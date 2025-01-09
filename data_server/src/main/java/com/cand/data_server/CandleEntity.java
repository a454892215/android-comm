package com.cand.data_server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 示例实体类
@Entity
class CandleEntity {
//    @Column(name = "id", nullable = false)
//    private int id;

    @Column(name = "date")
    private String date;

    @Column(name = "close")
    private double close;

    @Column(name = "open")
    private double open;

    @Column(name = "max")
    private double max;

    @Column(name = "min")
    private double min;
}
