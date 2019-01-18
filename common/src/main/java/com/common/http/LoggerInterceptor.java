package com.common.http;

import android.support.annotation.NonNull;

import com.common.BuildConfig;
import com.common.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String newSession = "token";
        // LogUtil.e("token",newSession );
        Request request = originalRequest.newBuilder()
                .header("Authorization", newSession)
                .build();

        if (BuildConfig.IS_DEBUG) logRequestInfo(request);
        Response response = chain.proceed(request);
        if (BuildConfig.IS_DEBUG) logResponseInfo(response);
        return response;
    }

    private void logResponseInfo(Response response) {
        try {
            long threadId = Thread.currentThread().getId();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                String string = buffer.clone().readString(Charset.forName("UTF-8"));
                LogUtil.debug("Response: threadId:" + threadId + " code:" + response.code() + ' ' + response.message() + " url:" + response.request().url() + string);
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
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String body = buffer.readString(Charset.forName("UTF-8"));
                LogUtil.debug("Request: threadId:" + threadId + " method:" + request.method() + "  " + request.url().uri() + body + " header:" + headers);
            } else {
                LogUtil.debug("Request: threadId:" + threadId + " method:" + request.url().uri() + "  header:" + headers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(" LLpp: " + e.toString());
        }
    }

}
