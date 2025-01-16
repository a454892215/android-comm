package com.cand.model;

import com.okex.open.api.test.ws.publicChannel.OkxApiModel;
import com.okex.open.api.test.ws.publicChannel.TickersEntity;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;

public class SubscribeModel {

    public static TradeChannelSubscribeEntity getTradeChannelSubscribeEntity() {
        TradeChannelSubscribeEntity entity = new TradeChannelSubscribeEntity();
        OkxApiModel okxModel = new OkxApiModel();
        TickersEntity tickers = okxModel.getTickers();
        for (int i = 0; i < tickers.data.size(); i++) {
            TickersEntity.Item datum = tickers.data.get(i);
            entity.args.add(new TradeChannelSubscribeEntity.Item(datum.instId));
        }
        return entity;
    }
}
