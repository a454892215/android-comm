package com.okex.open.api.test.ws.publicChannel.config;



import com.cand.util.LogUtil;
import com.google.gson.Gson;
import com.okex.open.api.bean.other.OrderBookItem;
import com.okex.open.api.bean.other.SpotOrderBook;
import com.okex.open.api.test.ws.publicChannel.PublicChannelTest;


import okhttp3.*;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"rawtypes", "unused"})
public class WebSocketClient {
    private static WebSocket webSocket = null;
    private static Boolean flag = false;
    private static Boolean isConnect = false;
    private static final Map<String,Optional<SpotOrderBook>> bookMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(PublicChannelTest.class);
    public WebSocketClient() {
    }



    //与服务器建立连接，参数为服务器的URL
    public static void connection(final String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            ScheduledExecutorService service;

            @Override
            public void onOpen(@NotNull final WebSocket webSocket, @NotNull final Response response) {
                //连接成功后，设置定时器，每隔25s，自动向服务器发送心跳，保持与服务器连接
                isConnect = true;
                LogUtil.d(Instant.now().toString() + " 连接到websocket成功：" + url);
                Runnable runnable = () -> sendMessage("ping");
                service = Executors.newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 25, 25, TimeUnit.SECONDS);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                LogUtil.e("准备关闭连接！...");
                webSocket.close(1000, "连接关闭...");
            }

            @Override
            public void onClosed(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
                LogUtil.d("websocket 连接已经关闭！");
            }

            @Override
            public void onFailure(@NotNull final WebSocket webSocket, @NotNull final Throwable t, final Response response) {
                LogUtil.d("websocket连接失败...:" + t.getCause());
                if (Objects.nonNull(service)) {
                    service.shutdown();
                }
            }

            @Override
            public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String bytes) {
            }
        });
    }

    private static void handleSd(WebSocket webSocket, String bytes) {

    }


    private static void isLogin(String s) {
        if (null != s && s.contains("login")) {
            if (s.endsWith("true}")) {
                flag = true;
            }
        }
    }

    private static <T extends OrderBookItem<?>> String getStr(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice().toString());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice().toString());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }
        return str;
    }

//    public static <T extends OrderBookItem<?>> int checksum(List<T> asks, List<T> bids) {
//        LogUtil.d("深度");
//        StringBuilder s = new StringBuilder();
//        for (int i = 0; i < 25; i++) {
//            if (i < bids.size()) {
//                s.append(bids.get(i).getPrice());
//                s.append(":");
//                s.append(bids.get(i).getSize());
//                s.append(":");
//            }
//            if (i < asks.size()) {
//                s.append(asks.get(i).getPrice());
//                s.append(":");
//                s.append(asks.get(i).getSize());
//                s.append(":");
//            }
//        }
//        final String str;
//        if (s.length() > 0) {
//            str = s.substring(0, s.length() - 1);
//        } else {
//            str = "";
//        }
//
//        return crc32.hashString(str, StandardCharsets.UTF_8).asInt();
//    }

    //获得sign
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(CharsetEnum.UTF_8.charset()), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes(CharsetEnum.UTF_8.charset()));
            hash = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            LogUtil.d("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    //登录
    public static void login(String apiKey, String passPhrase, String secretKey) {
        String timestamp = System.currentTimeMillis() / 1000+ "";
        String message = timestamp + "GET" + "/users/self/verify";
        String sign = sha256_HMAC(message, secretKey);
        String str = "{\"op\"" + ":" + "\"login\"" + "," + "\"args\"" + ":" + "[{" + "\"apiKey\"" + ":"+ "\"" + apiKey + "\"" + "," + "\"passphrase\"" + ":" + "\"" + passPhrase + "\"" + ","+ "\"timestamp\"" + ":"  + "\"" + timestamp + "\"" + ","+ "\"sign\"" + ":"  + "\"" + sign + "\"" + "}]}";
        sendMessage(str);
    }


    //订阅，参数为频道组成的集合
    public static void subscribe(List<Map> list) {
        String s = new Gson().toJson(list);
        String str = "{\"op\": \"subscribe\", \"args\":" + s + "}";
        if (null != webSocket)
            sendMessage(str);
    }

    public static void subscribe2(String json) {
        if (null != webSocket)
            sendMessage(json);
    }

    //取消订阅，参数为频道组成的集合
    public static void unsubscribe(List<Map> list) {
        String s = new Gson().toJson(list);
        String str = "{\"op\": \"unsubscribe\", \"args\":" + s + "}";
        if (null != webSocket)
            sendMessage(str);
    }

    private static void sendMessage(String str) {
        if (null != webSocket) {
            try {
                Thread.sleep(1300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!"ping".equals(str)){
                LogUtil.d(" 发送给服务端的信息是:" + str);
            }

            webSocket.send(str);
        } else {
            LogUtil.d("Please establish the connection before you operate it！");
        }
    }

    //断开连接
    public static void closeConnection() {
        if (null != webSocket) {
            webSocket.close(1000, "User actively closes the connection");
        } else {
            LogUtil.d("Please establish the connection before you operate it！");
        }
    }

    public boolean getIsLogin() {
        return flag;
    }

    public boolean getIsConnect() {
        return isConnect;
    }




    public static class OrderBookData {
        private List<List<String>> asks;
        private List<List<String>> bids;
        private String ts;
        private int checksum;

        public long getPrevSeqId() {
            return prevSeqId;
        }

        public void setPrevSeqId(long prevSeqId) {
            this.prevSeqId = prevSeqId;
        }

        public long getSeqId() {
            return seqId;
        }

        public void setSeqId(long seqId) {
            this.seqId = seqId;
        }

        private long prevSeqId;
        private long seqId;

        public List<List<String>> getAsks() {
            return asks;
        }

        public void setAsks(List<List<String>> asks) {
            this.asks = asks;
        }

        public List<List<String>> getBids() {
            return bids;
        }

        public void setBids(List<List<String>> bids) {
            this.bids = bids;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public int getChecksum() {
            return checksum;
        }

        public void setChecksum(int checksum) {
            this.checksum = checksum;
        }
    }
}
