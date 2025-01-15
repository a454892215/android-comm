package com.cand.server;

import com.cand.util.ThreadU;
import com.google.gson.Gson;
import com.okex.open.api.test.ws.publicChannel.OkxModel;
import com.okex.open.api.test.ws.publicChannel.TickersEntity;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.junit.Before;
import org.junit.Test;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class ExchangeTickerDataServerTest {


    @Before
    public void setLogLevel(){
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
    }
    @Test
    public void test() {
        try {
            //  连接到websocket成功  wss://ws.okx.com:8443/ws/v5/public
            //  连接到 websocket成功 wss://ws.okx.com:8443/ws/v5/public
            ExchangeTickerDataServer server = new ExchangeTickerDataServer();
            server.connection(WebSocketConfig.SERVICE_URL + "/ws/v5/public");
//            server.setOnOpeOkListener(() -> {
//                ThreadU.sleep(1000);
//                TradeChannelSubscribeEntity entity = new TradeChannelSubscribeEntity();
//                OkxModel okxModel = new OkxModel();
//                TickersEntity tickers = okxModel.getTickers();
//                for (int i = 0; i < tickers.data.size(); i++) {
//                    TickersEntity.Item datum = tickers.data.get(i);
//                    entity.args.add(new TradeChannelSubscribeEntity.Item(datum.instId));
//                    if(i > 6){
//                        break;
//                    }
//                }
//                String json = new Gson().toJson(entity);
//                server.subscribe(json);
//            });
            Thread.sleep(1000 * 60 * 60 * 24 * 36500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDynamicAddSubscribe(){
        try {
            //  连接到websocket成功  wss://ws.okx.com:8443/ws/v5/public
            //  连接到 websocket成功 wss://ws.okx.com:8443/ws/v5/public
            ExchangeTickerDataServer server = new ExchangeTickerDataServer();
            server.connection(WebSocketConfig.SERVICE_URL + "/ws/v5/public");
//            server.setOnOpeOkListener(() -> {
//                ThreadU.sleep(1000);
//                TradeChannelSubscribeEntity entity = new TradeChannelSubscribeEntity();
//                OkxModel okxModel = new OkxModel();
//                TickersEntity tickers = okxModel.getTickers();
//                for (int i = 0; i < tickers.data.size(); i++) {
//                    TickersEntity.Item datum = tickers.data.get(i);
//                    entity.args.add(new TradeChannelSubscribeEntity.Item(datum.instId));
//                }
//                String json = new Gson().toJson(entity);
//               // server.subscribe(json);
//            });

            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                ThreadU.sleep(1000 * 60);
                TradeChannelSubscribeEntity entity = new TradeChannelSubscribeEntity();
                OkxModel okxModel = new OkxModel();
                TickersEntity tickers = okxModel.getTickers();
                for (int i = 0; i < tickers.data.size(); i++) {
                    TickersEntity.Item datum = tickers.data.get(i);
                    entity.args.add(new TradeChannelSubscribeEntity.Item(datum.instId));
                }
                String json = new Gson().toJson(entity);
              //  server.subscribe(json);

            });
            Thread.sleep(1000 * 60 * 60 * 24 * 36500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
