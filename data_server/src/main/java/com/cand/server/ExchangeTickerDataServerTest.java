package com.cand.server;

import com.cand.util.ThreadU;
import com.google.gson.Gson;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.junit.Test;

public class ExchangeTickerDataServerTest {

    @Test
    public void test() {
        try {
            //  连接到websocket成功  wss://ws.okx.com:8443/ws/v5/public
            //  连接到 websocket成功 wss://ws.okx.com:8443/ws/v5/public
            ExchangeTickerDataServer server = new ExchangeTickerDataServer();
            server.connection(WebSocketConfig.SERVICE_URL + "/ws/v5/public");
            server.setOnOpeOkListener(() -> {
                ThreadU.sleep(1000);
                TradeChannelSubscribeEntity entity = new TradeChannelSubscribeEntity();
                entity.args.add(new TradeChannelSubscribeEntity.Item("EOS-USDT-SWAP"));
                entity.args.add(new TradeChannelSubscribeEntity.Item("SOL-USDT-SWAP"));
                String json = new Gson().toJson(entity);
                server.subscribe(json);
            });
            Thread.sleep(1000 * 60 * 60 * 24 * 36500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
