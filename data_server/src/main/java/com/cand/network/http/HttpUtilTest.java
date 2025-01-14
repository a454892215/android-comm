package com.cand.network.http;

import com.cand.util.LogUtil;

public class HttpUtilTest {
    public static void main(String[] args) {
        testLocalHttpServer();
    }

    private static void testLocalHttpServer() {
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.get("http://localhost:8080/?clientId=client77");
        LogUtil.d("result:" + result);
    }
}
