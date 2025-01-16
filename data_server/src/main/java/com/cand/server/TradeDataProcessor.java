package com.cand.server;

import com.cand.data_base.CandleEntity;
import com.cand.data_base.H2TableGenerator;
import com.cand.data_base.Repository;
import com.cand.entity.TradeEntity;
import com.cand.util.BigDecimalU;
import com.cand.util.LogUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TradeDataProcessor {
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>(); // 用来存储每个 coinId 的锁
    private final ConcurrentHashMap<String, TradeEntity> lastSavedDataMap = new ConcurrentHashMap<>();
    private final Repository repository = new Repository();

    public void handleTradeEntity(TradeEntity newest) {
        // 获取或创建一个 ReentrantLock，确保每个 coinId 有一个独立的锁
        ReentrantLock lock = lockMap.computeIfAbsent(newest.coinId, k -> new ReentrantLock());
        // 使用该锁对象同步方法，确保同一个 coinId 只能顺序执行
        lock.lock();
        try {
            TradeEntity last = lastSavedDataMap.getOrDefault(newest.coinId, null);
            String tableName = newest.getTableName();
            if (last == null) {
                if (!Repository.exists(tableName)) {
                    H2TableGenerator.generateTable(CandleEntity.class, tableName);
                }
                CandleEntity lastSavedCandle = repository.getNewestInsertEntity(CandleEntity.class, tableName);
                if (lastSavedCandle == null) {
                    repository.insertEntity(getCandleEntityByTradeEntity(newest), tableName);
                    lastSavedDataMap.put(newest.coinId, newest);
                    LogUtil.d2(newest.coinId + "表没有数据，第一次插入到数据库表：" + tableName + " price:" + newest.price);
                } else {
                    checkAndSaveNewestDataToLocal(newest, tableName, getTradeEntity(lastSavedCandle, newest.coinId));
                }
            } else {
                checkAndSaveNewestDataToLocal(newest, tableName, last);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void checkAndSaveNewestDataToLocal(TradeEntity newest, String tableName, TradeEntity lastSavedTradeP) {
        try {
            double fd = BigDecimalU.getFd(lastSavedTradeP.getPrice(), newest.getPrice());
            if (Math.abs(fd) >= 0.2) {
                repository.insertEntity(getCandleEntityByTradeEntity(newest), tableName);
                lastSavedDataMap.put(newest.coinId, newest);
                // LogUtil.d(newest.coinId + " 插入到数据库表：" + tableName + " price:" + newest.price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TradeEntity getTradeEntity(CandleEntity newestEntity, String coinId) {
        if (newestEntity != null) {
            TradeEntity last = new TradeEntity();
            last.coinId = coinId;
            last.ts = newestEntity.getLongTimestamp(); // 时间戳
            last.price = newestEntity.getOpen().toPlainString(); // 成交价格
            last.size = newestEntity.getVolume().toPlainString(); // 成交数量
            return last;
        }
        return null;
    }

    public CandleEntity getCandleEntityByTradeEntity(TradeEntity entity) {
        if (entity != null) {
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
