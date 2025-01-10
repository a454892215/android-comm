package com.network.http;

import com.util.LogUtil;

import java.net.CookieManager;
import java.net.CookiePolicy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author: Pan
 * 2022/5/14
 * Description:
 */
public class HttpUtil {

    private String BASE_URL;
    private final static String BASE_URL_1 = "https://8.210.49.248:";

    private OkHttpClient client = new OkHttpClient();
    private int getRequestLxFailCount;

    public HttpUtil() {
        BASE_URL = BASE_URL_1 + 80;
        init();
    }

    public void setPort(int port) {
        BASE_URL = BASE_URL_1 + port;
    }

    public void setPosInfoPort(){
        setPort(8008);
    }

    public void setLocalBaseUrl() {
        BASE_URL = "https://127.0.0.1:80";
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        // builder.cookieJar(new JavaNetCookieJar(cookieManager));
        builder.connectTimeout(13, TimeUnit.SECONDS);
        builder.writeTimeout(13, TimeUnit.SECONDS);
        builder.readTimeout(13, TimeUnit.SECONDS);
     //   ConnectionPool poll = new ConnectionPool(1, 5L, TimeUnit.MINUTES);
       // builder.connectionPool(poll);
        builder.hostnameVerifier((hostname, session) -> true);
        builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), new TrustAllCerts());
        builder.addInterceptor(new LogInterceptor());
        client = builder.build();
    }




    public String get(String url) {
        String string = null;
        try {
            //  LogUtil.d("get url:" + url);
            Request.Builder builder = new Request.Builder().get();
            Request request = builder.url(url)
                    .addHeader("Connection", "close") // keep-alive 或 close
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                string = body.string();
            }
            response.close();
            getRequestLxFailCount = 0;
        } catch (Exception e) {
            getRequestLxFailCount++;
            if (getRequestLxFailCount >= 3) {
                // SoundUtil.forcePlay();
            }
            LogUtil.e("Get请求异常==33：" + " LxFailCount:" + getRequestLxFailCount + " :" + e + " url:" + url);
        }
        return string;
    }

    private String post(String url, RequestBody requestBody) {
        String string = null;
        LogUtil.d("post url:" + url);
        try {
            Request.Builder builder = new Request.Builder();
            Request request = builder
                    .url(url)
                    .addHeader("Connection", "close") // keep-alive 或 close
                    .addHeader("content-type", "application/json")
                    .addHeader("Referer", "https://127.0.0.1:80")
                    .method("POST", requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                string = body.string();
            }
            response.close();
        } catch (Exception e) {
            // SoundUtil.forcePlay();
            LogUtil.e("请求异常22：" + e);
        }
        return string;
    }



    @SuppressWarnings("unused")
    public static void test2() {
        HttpUtil httpUtil = new HttpUtil();

    }

    public static void main(String[] args) {
        test2();
    }
}
