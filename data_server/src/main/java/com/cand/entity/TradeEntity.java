package com.cand.entity;

import java.math.BigDecimal;

public class TradeEntity {
    public String coinId;
    public String price; // 成交价格
    public long ts; // Unix时间戳的毫秒数格式
    public String size; // 成交数量

    public TradeEntity() {
    }

    public TradeEntity(String coinId, String price, long ts, String size) {
        this.coinId = coinId;
        this.price = price;
        this.ts = ts;
        this.size = size;
    }

    public BigDecimal getPrice() {
        return new BigDecimal(price);
    }

    public BigDecimal getSize() {
        return new BigDecimal(size);
    }

    public long getDTimeToTar(TradeEntity other){
        return this.ts - other.ts;
    }

    @Override
    public String toString() {
        return "TradeEntity{" +
                "coinId='" + coinId + '\'' +
                ", price='" + price + '\'' +
                ", ts=" + ts +
                ", size='" + size + '\'' +
                '}';
    }

    public String getTableName() {
        return "candle01_" + coinId.toLowerCase().replace("-", "_")+"_fd";
    }
}
