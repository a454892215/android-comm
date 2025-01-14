package com.cand.test;

import com.cand.util.LogUtil;

import io.undertow.util.Headers;
import io.undertow.Undertow;

// http://localhost:8080/?clientId=client1
public class AsyncUndertowExample {


    public static void main(String[] args) {
        // 创建并启动 Undertow 服务器
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost") // 监听本地 8080 端口
                .setHandler(exchange -> {
                    String path = exchange.getRequestPath();
                    // 获取客户端请求参数
                    String clientId = exchange.getQueryParameters().get("clientId").getFirst();
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

                    // 异步处理请求
                    exchange.dispatch(() -> {
                        try {
                            // 模拟等待操作（如从数据库获取数据）
                            //   simulateLongPoll(clientId);
                            // 响应客户端
                            if ("/favicon.ico".equals(path)) {
                                // 忽略 favicon.ico 请求或返回 404
                                exchange.setStatusCode(404).getResponseSender().send("");
                                LogUtil.d("忽略了：" + path + " 请求");
                                return;
                            }
                            exchange.getResponseSender().send("Hello, " + clientId + "! Data received.");
                            LogUtil.d("响应成功发送end：" + clientId);
                        } catch (Exception e) {
                            LogUtil.d("响应异常：" + clientId);
                            e.printStackTrace();
                            //  exchange.setStatusCode(500).getResponseSender().send("Internal Server Error");
                        }
                    });
                }).build();

        server.start(); // 启动服务器
        LogUtil.d("Async Undertow server started on http://localhost:8080");
    }

    /**
     * 模拟长轮询或等待操作
     *
     * @param clientId 客户端 ID
     */
    private static void simulateLongPoll(String clientId) {
        try {
            LogUtil.d("Waiting for data from client: " + clientId);
            Thread.sleep(5000); // 模拟等待数据的操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
