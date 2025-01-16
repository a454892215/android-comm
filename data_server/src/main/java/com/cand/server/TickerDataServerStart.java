package com.cand.server;

import com.cand.model.SubscribeModel;
import com.cand.util.LogUtil;
import com.cand.util.ThreadU;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class TickerDataServerStart {

    public void start() {
        try {
            Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.setLevel(Level.INFO);
            // 连接到websocket成功  wss://ws.okx.com:8443/ws/v5/public
            String url = WebSocketConfig.SERVICE_URL + "/ws/v5/public";
            ExchangeTickerDataServer server = new ExchangeTickerDataServer();
            server.setTradeChannelSubscribeTask(SubscribeModel.getTradeChannelSubscribeEntityFromApi());
            server.connection(url);
            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                // noinspection InfiniteLoopStatement
                while (true) {
                    try {
                        ThreadU.sleep(1000 * 60);
                        TradeChannelSubscribeEntity newEntity = SubscribeModel.getTradeChannelSubscribeEntityFromApi();
                        if (!newEntity.equals(server.getTradeChannelSubscribeTask())) {
                            LogUtil.d("发现服务器websocket可订阅的币种改变，准备重新启动websocket...");
                            server.setTradeChannelSubscribeTask(newEntity);
                            server.restartWebSocket(url);
                        }
                    } catch (Exception e) {
                        LogUtil.d(e.toString());
                    }
                }

            });
            Thread.sleep(1000 * 60 * 60 * 24 * 36500L);
        } catch (Exception e) {
            LogUtil.d(e.toString());
            e.printStackTrace();
        }
    }
}
