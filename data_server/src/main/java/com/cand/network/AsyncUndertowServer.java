package com.cand.network;

import com.cand.util.LogUtil;

import io.undertow.Undertow;
import io.undertow.util.Headers;

// http://localhost:8080/?clientId=client1
public class AsyncUndertowServer {


    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(exchange -> {
                    String path = exchange.getRequestPath();
                    String clientId = exchange.getQueryParameters().get("clientId").getFirst();
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.dispatch(() -> {
                        try {
                            if ("/favicon.ico".equals(path)) {
                                // 忽略 favicon.ico 请求或返回 404
                                exchange.setStatusCode(404).getResponseSender().send("");
                                LogUtil.d("忽略了：" + path + " 请求");
                                return;
                            }
                            exchange.getResponseSender().send("Hello, " + clientId + "! Data received.");
                            LogUtil.d("响应成功发送end：" + clientId);
                        } catch (Exception e) {
                            exchange.setStatusCode(500).getResponseSender().send("Internal Server Error");
                            LogUtil.d("响应异常：" + clientId);
                            e.printStackTrace();
                        }
                    });
                }).build();

        server.start(); // 启动服务器
        LogUtil.d("启动服务器: http://localhost:8080");
    }

}
