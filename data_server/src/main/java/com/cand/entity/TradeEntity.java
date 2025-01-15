package com.cand.entity;

public class TradeEntity {
    public String coinId;
    public String price; // 成交价格
    public long ts; // Unix时间戳的毫秒数格式
    public String size; // 成交数量

    @Override
    public String toString() {
        return "TradeEntity{" +
                "coinId='" + coinId + '\'' +
                ", price='" + price + '\'' +
                ", ts=" + ts +
                ", size='" + size + '\'' +
                '}';
    }


}
