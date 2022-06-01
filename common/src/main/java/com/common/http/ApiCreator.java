package com.common.http;

import com.common.http.inter.IApiCreator;
import com.common.http.other.HttpSSLSetting;
import com.common.http.other.LogInterceptor;
import com.common.http.other.TrustAllCerts;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
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

    private ConnectionPool connectionPool;

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
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        connectionPool = new ConnectionPool(32, 5, TimeUnit.MINUTES);
        builder.connectionPool(connectionPool);
        if(defaultHttpsEnable){
            builder.hostnameVerifier((hostname, session) -> true);
            builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), new TrustAllCerts());
        }
        return builder.build();
    }

    private boolean defaultHttpsEnable = true;

    public void setDefaultHttpsEnable(boolean defaultHttpsEnable){
        this.defaultHttpsEnable = defaultHttpsEnable;
    }

    int connectTimeout = 60;
    public void setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
    }

    int writeTimeout = 60;
    public void setWriteTimeout(int writeTimeout){
        this.writeTimeout = writeTimeout;
    }

    int readTimeout = 60;
    public void setReadTimeout(int readTimeout){
        this.readTimeout = readTimeout;
    }


    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }
}
