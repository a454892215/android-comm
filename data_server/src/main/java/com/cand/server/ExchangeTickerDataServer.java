package com.cand.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cand.entity.TradeEntity;
import com.cand.util.LogUtil;
import com.okex.open.api.test.ws.publicChannel.TradeChannelSubscribeEntity;
import com.okex.open.api.utils.DateUtils;


import org.apache.commons.lang.time.DateFormatUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


@SuppressWarnings("unused")
public class ExchangeTickerDataServer {
    private static WebSocket webSocket = null;


    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    private final ScheduledExecutorService retryService = Executors.newSingleThreadScheduledExecutor();
    private boolean isReconnecting = false;

    private final TradeDataProcessor processor = new TradeDataProcessor();
    private TradeChannelSubscribeEntity tradeChannelSubscribeTask;

    public void connection(final String url) {
        Request request = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            ScheduledExecutorService heartbeatService;

            @Override
            public void onOpen(@NotNull final WebSocket webSocket, @NotNull final Response response) {
                LogUtil.d(Instant.now().toString() + " 连接到 websocket 成功:" + url);
                isReconnecting = false; // 重置重连状态
                Runnable heartbeatTask = () -> sendMessage("ping");
                heartbeatService = Executors.newSingleThreadScheduledExecutor();
                heartbeatService.scheduleAtFixedRate(heartbeatTask, 25, 25, TimeUnit.SECONDS);
                if(tradeChannelSubscribeTask != null){
                    // TODO
                }
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                LogUtil.e("准备关闭连接！...");
                webSocket.close(1000, "连接关闭...");
                shutdownHeartbeat();
            }

            @Override
            public void onClosed(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
                LogUtil.d("websocket 连接已经关闭！");
            }

            @Override
            public void onFailure(@NotNull final WebSocket webSocket, @NotNull final Throwable t, final Response response) {
                t.printStackTrace();
                LogUtil.d("websocket 连接失败...:" + t.getMessage());
                shutdownHeartbeat();
                retryConnection(url); // 触发重连逻辑
            }

            private void shutdownHeartbeat() {
                if (heartbeatService != null && !heartbeatService.isShutdown()) {
                    heartbeatService.shutdown();
                }
            }

            /**
             * 可能在不同的线程返回 需要注意线程安全问题
             */
            @Override
            public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String content) {
                try {
                    if ("pong".equals(content)) {
                    } else {
                        JSONObject jsonObject = JSON.parseObject(content);
                        JSONObject arg = jsonObject.getJSONObject("arg");
                        String channel = arg.getString("channel");
                        if ("trades".equals(channel)) {
                            // https://www.okx.com/docs-v5/zh/#order-book-trading-market-data-ws-trades-channel
                            // {"arg":{"channel":"trades","instId":"SOL-USDT-SWAP"},"data":[{"instId":"SOL-USDT-SWAP","tradeId":"620869571","px":"187.08","sz":"6.28","side":"sell","ts":"1736909125942","count":"7"}]}
                            JSONArray dataArr = jsonObject.getJSONArray("data");
                            if (dataArr != null) {
                                int size = dataArr.size();
                                for (int i = 0; i < size; i++) {
                                    JSONObject item = dataArr.getJSONObject(i);
                                    TradeEntity last = new TradeEntity();
                                    last.coinId = item.getString("instId");
                                    last.ts = item.getLong("ts"); //时间戳
                                    last.price = item.getString("px"); //成交价格
                                    last.size = item.getString("sz"); // 成交数量
                                    processor.handleTradeEntity(last);
                                    long delay = System.currentTimeMillis() - last.ts;
                                    if (Math.abs(delay) > 400) {
                                        LogUtil.e("服务器返回数据延迟超标delay：" + delay + "  " + last.coinId + "  " + DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4));
                                    }

                                }
                            }else{
                                // TODO 记录已经订阅过的币种
                            }
                        }
                    }
                    //  LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "======> Receive: " + content);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("处理数据发送异常：" + e);
                }
            }
        });
    }


    private void retryConnection(final String url) {
        if (isReconnecting) {
            return; // 防止多次触发重连逻辑
        }
        isReconnecting = true;
        retryService.schedule(() -> {
            LogUtil.d("正在尝试重新连接...");
            connection(url); // 尝试重新建立连接
        }, 30, TimeUnit.SECONDS);
    }

    private void sendMessage(String str) {
        if (webSocket != null) {
            try {
                Thread.sleep(1300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!"ping".equals(str)) {
                LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " 发送给服务端的信息是:" + str);
            }
            webSocket.send(str);
        } else {
            LogUtil.d("webSocket 是 NULL 异常！");
        }
    }


    public void setTradeChannelSubscribeTask(TradeChannelSubscribeEntity tradeChannelSubscribeTask) {
        this.tradeChannelSubscribeTask = tradeChannelSubscribeTask;
    }



    public void subscribe(String json) {
        if (webSocket != null) {
            sendMessage(json);
        }

    }


}

