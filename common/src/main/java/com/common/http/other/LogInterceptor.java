package com.common.http.other;

import androidx.annotation.NonNull;

import com.common.utils.LogUtil;

import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    private boolean logEnable;

    public LogInterceptor(boolean logEnable) {
        this.logEnable = logEnable;
    }

    @Override
    public Response intercept(@NonNull Chain chain) {
        Response response = null;
        try {
            Request request = chain.request();
            if (logEnable) logRequestInfo(request);
            response = chain.proceed(request);
            if (logEnable) logResponseInfo(response);
        } catch (Exception e) {
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
                LogUtil.d("Response: threadId:" + threadId + " method :" + method + "  message:" + response.message()
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
            if (header_keys != null) {
                for (Object header_key1 : header_keys) {
                    String header_key = (String) header_key1;
                    headerBuilder.append(header_key).append(":").append(headers.get(header_key)).append("   ");
                }
            }
            String method = request.method();
            String body = "";
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                body = buffer.readString(Charset.forName("UTF-8"));
            }
            LogUtil.d("Request: threadId:" + threadId + " method:" + method + " url:" + request.url().uri() + "  body:" + body + " header:" + headerBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(" LLpp: " + e.toString());
        }
    }

}
