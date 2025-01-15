package com.cand.server;

import com.cand.data_base.CandleEntity;
import com.cand.data_base.H2TableGenerator;
import com.cand.data_base.Repository;
import com.cand.entity.TradeEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class TradeDataProcessor {

    private final Map<String, TradeEntity> lastSavedDataMap = new HashMap<>();

    public void handleTradeEntity(TradeEntity entity) {
        try {
            TradeEntity last = lastSavedDataMap.getOrDefault(entity.coinId, null);
            if (last == null) {
                // 先从数据库获取最近一条数据
                Repository repository = new Repository();
                String tableName = entity.getTableName();
                if (!Repository.exists(tableName)) {
                    H2TableGenerator.generateTable(CandleEntity.class, tableName);
                }
                CandleEntity newestEntity = repository.getNewestInsertEntity(CandleEntity.class, tableName);
                if (newestEntity == null) {
                    last = entity;
                    lastSavedDataMap.put(entity.coinId, last);
                    CandleEntity newEntity = new CandleEntity();
                    newEntity.setLongTimestamp(last.ts);
                    newEntity.setOpen(new BigDecimal(last.price));
                    newEntity.setHigh(new BigDecimal(last.price));
                    newEntity.setLow(new BigDecimal(last.price));
                    newEntity.setVolume(new BigDecimal(last.size));
                   // repository.insertEntity(newEntity, tableName);
                } else {
                    long timestamp = newestEntity.getTimestamp().toEpochSecond(ZoneOffset.UTC);
                    last = new TradeEntity(entity.coinId, newestEntity.getOpen() + "", timestamp, newestEntity.getVolume() + "");
                }
                lastSavedDataMap.put(entity.coinId, entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
