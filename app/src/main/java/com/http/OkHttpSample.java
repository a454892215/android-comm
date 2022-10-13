package com.http;

import androidx.annotation.NonNull;

import com.common.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class OkHttpSample {
    /**
     * 同步请求示例
     */
    private void syncRequest() {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build(); //构建携带请求信息的Request对象
        Call call = client.newCall(request);//RealCall，传入request，构建出Call对象
        try {
            Response response = call.execute();//dispatcher.executed()：把请求加入到同步请求队列,获取到response后，移除掉同步请求。
            ResponseBody body = response.body();
            if (body != null) {
                LogUtil.d(body.string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求示例
     */
    private void asyncRequest() {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build(); //构建携带请求信息的Request对象
        Call call = client.newCall(request);//RealCall，传入request，构建出Call对象
        try {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    ResponseBody body = response.body();
                    if (body != null) {
                        LogUtil.d(body.string());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
