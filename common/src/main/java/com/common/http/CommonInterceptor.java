package com.common.http;

import android.support.annotation.NonNull;

import com.common.AppApplication;
import com.common.BuildConfig;
import com.common.utils.LogUtil;
import com.common.utils.SharedPreUtils;

import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) {
        Response response = null;
        try {
            Request request = chain.request();
            String newSession = SharedPreUtils.getString(AppApplication.sContext, AppApplication.TOKE_KEY, "");
            if (request.url().toString().endsWith("/config")) {
                request = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Connection", "Keep-Alive")
                        .build();
            } else {
                request = request.newBuilder()
                        .header("Authorization", newSession)
                      //  .header("Accept", "application/x.tg.v2+json")
                        .build();
            }
            if (BuildConfig.IS_DEBUG) logRequestInfo(request);
            response = chain.proceed(request);
            if (BuildConfig.IS_DEBUG) logResponseInfo(response);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            LogUtil.e("loggerInterceptor 异常=========: " + e.toString());
        }
        return response;
    }

    private void logResponseInfo(Response response) {
        try {
            long threadId = Thread.currentThread().getId();
            String method = response.request().method();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                String text = buffer.clone().readString(Charset.forName("UTF-8"));
                LogUtil.debug("Response: threadId:" + threadId + " method :" + method + "  message:" + response.message()
                        + " url:" + response.request().url() + " code:" + response.code() + " 响应内容：" + text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("=========: " + e.toString());
        }
    }

    private static synchronized void logRequestInfo(Request request) {
        try {
            long threadId = Thread.currentThread().getId();
            RequestBody requestBody = request.body();
            Headers headers = request.headers();
            Object[] header_keys = headers.names().toArray();
            StringBuilder headerBuilder = new StringBuilder();
            for (Object header_key1 : header_keys) {
                String header_key = (String) header_key1;
                headerBuilder.append(header_key).append(":").append(headers.get(header_key)).append("   ");
            }
            String method = request.method();
            String body = "";
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                body = buffer.readString(Charset.forName("UTF-8"));
            }
            LogUtil.debug("Request: threadId:" + threadId + " method:" + method + " url:" + request.url().uri() + "  body:" + body + " header:" + headerBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(" LLpp: " + e.toString());
        }
    }

}
