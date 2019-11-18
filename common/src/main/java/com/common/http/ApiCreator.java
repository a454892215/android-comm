package com.common.http;

import com.common.http.inter.IApiCreator;
import com.common.http.other.HttpSSLSetting;
import com.common.http.other.LogInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:  L
 * CreateDate: 2018/11/26 8:51
 * Description: No
 */

public class ApiCreator implements IApiCreator {

    private boolean logEnable = false;

    private List<Interceptor> interceptorList = new ArrayList<>();

    @Override
    public <T> T getApi(String baseUrl, Class<T> api) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(api);
    }

    @Override
    public void logEnable(boolean enable) {
        this.logEnable = enable;
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(false);
        if(logEnable){
            builder.addInterceptor(new LogInterceptor());
        }
        for (int i = 0; i < interceptorList.size(); i++) {
            builder.addInterceptor(interceptorList.get(i));
        }
        //设置cookie
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        HttpSSLSetting.SSLParams sslParams1 = HttpSSLSetting.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        return builder.build();
    }


    @Override
    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }
}
