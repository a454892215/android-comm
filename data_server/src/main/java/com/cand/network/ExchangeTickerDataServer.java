package com.cand.network;

import com.alibaba.fastjson.JSONArray;
import com.cand.util.LogUtil;
import com.google.gson.Gson;
import com.okex.open.api.utils.DateUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    private OnOpeOkListener onOpeOkListener;
    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    private final ScheduledExecutorService retryService = Executors.newSingleThreadScheduledExecutor();
    private boolean isReconnecting = false;

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
                if (onOpeOkListener != null) {
                    onOpeOkListener.onOk();
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
                LogUtil.d("websocket 连接失败...:" + t.getMessage());
                shutdownHeartbeat();
                retryConnection(url); // 触发重连逻辑
            }

            private void shutdownHeartbeat() {
                if (heartbeatService != null && !heartbeatService.isShutdown()) {
                    heartbeatService.shutdown();
                }
            }

            @Override
            public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String content) {
                if (content.equals("pong")) {
                    LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "======> Receive: " + content);
                }else{
                    JSONObject rst = JSONObject.fromObject(content);
                    net.sf.json.JSONArray dataArr = net.sf.json.JSONArray.fromObject(rst.get("data"));
                    JSONObject data = JSONObject.fromObject(dataArr.get(0));
                    long localTimestamp = System.currentTimeMillis();
                    long ts = Long.parseLong(data.get("ts").toString());
                    long timing = localTimestamp - ts;
                    LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "(" + timing + "ms)" + " Receive: " + content);
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
            if (!"ping".equals(str)) {
                LogUtil.d(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " 发送给服务端的信息是:" + str);
            }
            webSocket.send(str);
        } else {
            LogUtil.d("webSocket 是 NULL 异常！");
        }
    }

    public void setOnOpeOkListener(OnOpeOkListener onOpeOkListener) {
        this.onOpeOkListener = onOpeOkListener;
    }

    private String listToJsonStr(List<Map<String, String>> list) {
        JSONArray jsonArray = new JSONArray();
        for (Map<String, String> map : list) {
            jsonArray.add(JSONObject.fromObject(map));
        }
        return jsonArray.toJSONString();
    }

    public void subscribe(String json) {
        if (webSocket != null){
            sendMessage(json);
        }

    }


}

