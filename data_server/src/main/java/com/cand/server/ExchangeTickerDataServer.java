package com.cand.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cand.entity.TradeEntity;
import com.cand.util.LogUtil;
import com.google.gson.Gson;
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
                if (tradeChannelSubscribeTask != null) {
                    LogUtil.d2("开始订阅ticker数据，订阅的币种数目是：" + tradeChannelSubscribeTask.args.size());
                    String json = new Gson().toJson(tradeChannelSubscribeTask);
                    sendMessage(json);
                }
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                LogUtil.d2("准备关闭连接！...");
                webSocket.close(1000, "连接关闭...");
                shutdownHeartbeat();
            }

            @Override
            public void onClosed(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
                LogUtil.d("websocket 连接已经关闭！");
            }

            @Override
            public void onFailure(@NotNull final WebSocket webSocket, @NotNull final Throwable t, final Response response) {
                try {
                    LogUtil.d("websocket onFailure 准备关闭websocket 重新连接:" + t.getMessage());
                    shutdownHeartbeat();
                    closeWebSocket();
                    retryConnection(url);
                } catch (Exception e) {
                 LogUtil.e(e.toString());
                }

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
                                    if (Math.abs(delay) > 700) {
                                        LogUtil.e("服务器返回数据延迟超标delay：" + delay + "  " + last.coinId + "  " + DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4));
                                    }
                                }
                            }
                        }else{
                            LogUtil.d("收到的未处理信息是:" + content);
                        }
                    }
                    //  LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "======> Receive: " + content);
                } catch (Exception e) {
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
            LogUtil.d("正在尝试重新连接...:" + url);
            connection(url); // 尝试重新建立连接
        }, 30, TimeUnit.SECONDS);
    }

    public void restartWebSocket(final String url) {
        // 关闭当前的 WebSocket 连接
        closeWebSocket();
        retryService.schedule(() -> {
            LogUtil.d("开始重新启动 WebSocket 连接...");
            connection(url);
        }, 30, TimeUnit.SECONDS);
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            try {
                LogUtil.d("正在关闭当前 WebSocket 连接...");
                webSocket.close(1000, "手动关闭连接");
            } catch (Exception e) {
                LogUtil.e("关闭 WebSocket 时出现异常：" + e.getMessage());
            } finally {
                webSocket = null;
            }
        } else {
            LogUtil.d("当前没有活动的 WebSocket 连接。");
        }
    }


    private void sendMessage(String str) {
        if (webSocket != null) {
            try {
                Thread.sleep(1300);
            } catch (Exception e) {
                LogUtil.e(e.toString());
            }
            if (!"ping".equals(str)) {
                String message = DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " 发送给服务端的信息是:";
                if (str.length() > 80) {
                    message += str.substring(0, 80) + "..."; // 只截取前40个字符，并加上省略号
                } else {
                    message += str; // 如果长度小于等于40，则直接打印
                }
                LogUtil.d2(message);
            }
            webSocket.send(str);
        } else {
            LogUtil.d("webSocket 是 NULL 异常！");
        }
    }

    public void setTradeChannelSubscribeTask(TradeChannelSubscribeEntity tradeChannelSubscribeTask) {
        this.tradeChannelSubscribeTask = tradeChannelSubscribeTask;
    }

    public TradeChannelSubscribeEntity getTradeChannelSubscribeTask() {
        return tradeChannelSubscribeTask;
    }

}

