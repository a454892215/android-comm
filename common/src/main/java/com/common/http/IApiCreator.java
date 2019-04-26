package com.common.http;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public interface IApiCreator {

    <T> T getApi(String baseUrl, Class<T> api);

    void logEnable(boolean enable);

    OkHttpClient getOkHttpClient();

    void addInterceptor(Interceptor interceptor);
}
