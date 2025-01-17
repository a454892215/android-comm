package com.cand.server;

import com.cand.model.SubscribeModel;
import com.cand.util.LogUtil;
import com.cand.util.ThreadU;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class TickerDataServerStart {
    ExchangeTickerDataServer server;

    public void start() {
        try {
            Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.setLevel(Level.INFO);
            // 连接到websocket成功  wss://ws.okx.com:8443/ws/v5/public
            String url = WebSocketConfig.SERVICE_URL + "/ws/v5/public";
            server = new ExchangeTickerDataServer();
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
            waitForStopSignal();
            ThreadU.sleep(1000 * 60 * 60 * 24 * 365L * 100);
        } catch (Exception e) {
            LogUtil.d(e.toString());
        }
    }

    public void waitForStopSignal() {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                // System.out.println("输入 'stop' 结束程序：");
                System.out.println("System.in is available: " + System.in.available());
                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if ("stop".equalsIgnoreCase(input.trim())) {
                        if (server != null) {
                            server.closeWebSocket();
                        }
                        LogUtil.d2("准备结束程序:" + input);
                        ThreadU.sleep(3000);
                        System.exit(0);
                        break;
                    } else {
                        LogUtil.d2("非法输入，不处理：" + input);
                    }
                }
                LogUtil.d("======waitForStopSignal 结束========");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public static void main(String[] args) {
        TickerDataServerStart start = new TickerDataServerStart();
        start.start();

    }
}
