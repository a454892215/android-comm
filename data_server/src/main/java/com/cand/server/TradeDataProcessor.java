package com.cand.server;

import com.cand.data_base.CandleEntity;
import com.cand.data_base.H2TableGenerator;
import com.cand.data_base.Repository;
import com.cand.entity.TradeEntity;

import java.math.BigDecimal;
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
                CandleEntity newestCandle= repository.getNewestInsertEntity(CandleEntity.class, tableName);
                TradeEntity newest = parse(newestCandle, entity.coinId);
                if (newest == null) {
                    last = entity;
                    lastSavedDataMap.put(entity.coinId, last);
                   // repository.insertEntity(newEntity, tableName);
                } else {
                   // long timestamp = newestEntity.getTimestamp().toEpochSecond(ZoneOffset.UTC);
                   // last = new TradeEntity(entity.coinId, newestEntity.getOpen() + "", timestamp, newestEntity.getVolume() + "");
                }
                lastSavedDataMap.put(entity.coinId, entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TradeEntity parse(CandleEntity newestEntity, String coinId){
        if(newestEntity != null){
            TradeEntity last = new TradeEntity();
            last.coinId = coinId;
            last.ts = newestEntity.getLongTimestamp(); //时间戳
            last.price = newestEntity.getOpen().toPlainString(); //成交价格
            last.size = newestEntity.getVolume().toPlainString(); // 成交数量
            return last;
        }
        return null;
    }

    public CandleEntity parse(TradeEntity entity){
        if(entity != null){
            CandleEntity newEntity = new CandleEntity();
            newEntity.setLongTimestamp(entity.ts);
            newEntity.setOpen(new BigDecimal(entity.price));
            newEntity.setClose(new BigDecimal(entity.price));
            newEntity.setHigh(new BigDecimal(entity.price));
            newEntity.setLow(new BigDecimal(entity.price));
            newEntity.setVolume(new BigDecimal(entity.size));
            return newEntity;
        }
        return null;
    }
}
