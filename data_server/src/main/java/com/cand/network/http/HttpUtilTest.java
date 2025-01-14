package com.cand.network.http;

import com.cand.util.LogUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtilTest {
    public static void main(String[] args) {
        test();
    }

    private static void testLocalHttpServer() {
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.get("http://localhost:8080/?clientId=client77");
        LogUtil.d("result:" + result);
    }

    private static void test(){
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();
        // 创建请求对象
        String url = "http://localhost:8080/?clientId=client77";
        Request request = new Request.Builder()
                .url(url)
                .build();
        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.err.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
