package com.cand.server;

import com.cand.entity.TradeEntity;

import java.util.HashMap;
import java.util.Map;

public class TradeDataProcessor {

    private final Map<String, TradeEntity> lastSavedDataMap = new HashMap<>();

    public void handleTradeEntity(TradeEntity entity){
        TradeEntity last = lastSavedDataMap.getOrDefault(entity.coinId, null);
        if(last == null){
            lastSavedDataMap.put(entity.coinId, entity);

        }
    }
}
