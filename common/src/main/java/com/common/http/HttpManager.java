package com.common.http;


import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.utils.ToastUtil;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author:  L
 * CreateDate: 2018/11/26 8:51
 * Description: No
 */

public class HttpManager {

    public static <T> T initRetrofit(String baseUrl, Class<T> api) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(api);

    }

    public static void requestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        startRequest(observable, httpCallback, null, false);
    }

    public static void requestData(Observable<ResponseBody> observable, HttpCallback httpCallback, BaseActivity activity, boolean isShowLoading) {
        startRequest(observable, httpCallback, activity, isShowLoading);
    }

    private static void startRequest(Observable<ResponseBody> observable, HttpCallback httpCallback, BaseActivity activity, boolean isShowLoading) {
        if (activity != null && !SystemUtils.isNetWorkConnected(activity)) {
            ToastUtil.showShort(activity, "请检测网络是否连接");
            return;
        }
        if (activity != null && isShowLoading) activity.showDefaultLoadingPop();
        observable.subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallback.onFail();
                        if (activity != null && isShowLoading) {
                            activity.dismissDefaultLoadingPop();
                        }

                        LogUtil.e("Http=====:" + e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String text = responseBody.string();
                            httpCallback.onSuccess(text);
                            if (activity != null && isShowLoading) {
                                activity.dismissDefaultLoadingPop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e("Http:===requestData====:" + e.toString());
                        }
                    }
                });
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new CommonInterceptor());
        //设置cookie
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        return builder.build();
    }

}
