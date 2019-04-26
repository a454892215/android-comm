package com.common.http;

import android.app.Activity;

import com.common.base.BaseActivity;
import com.common.http.inter.HttpCallback;
import com.common.http.inter.IHttpUtil;
import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.utils.ToastUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtil implements IHttpUtil {

    protected Activity activity;

    private boolean showLoadingEnable;
    private boolean checkNetworkEnable = true;
    private boolean successExcToastEnable = true;
    private boolean failToastEnable = true;

    public HttpUtil(Activity activity) {
        this.activity = activity;
    }

    @Override
    public HttpUtil successExcToastEnable(boolean enable) {
        this.successExcToastEnable = enable;
        return this;
    }

    @Override
    public HttpUtil failToastEnable(boolean enable) {
        this.failToastEnable = enable;
        return this;
    }

    @Override
    public HttpUtil showLoadingEnable(boolean enable) {
        this.showLoadingEnable = enable;
        return this;
    }

    @Override
    public HttpUtil checkNetworkEnable(boolean enable) {
        this.checkNetworkEnable = enable;
        return this;
    }

    @Override
    public void requestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        if (checkNetworkEnable) {
            if (!SystemUtils.isNetWorkConnected(activity)) {
                ToastUtil.showShort(activity, "请检测网络是否连接");
                return;
            }
        }
        startRequestData(observable, httpCallback);
    }


    public void startRequestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        if (activity != null && showLoadingEnable) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showDefaultLoadingView();
            }
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onRequestError(e, httpCallback);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onRequestSuccess(responseBody, httpCallback);
                    }
                });
    }

    @Override
    public void onRequestSuccess(ResponseBody responseBody, HttpCallback httpCallback) {
        try {
            if (activity != null && showLoadingEnable) {
                if (activity instanceof BaseActivity) {
                    ((BaseActivity) activity).dismissDefaultLoadingView();
                }
            }
            String text = responseBody.string();
            httpCallback.onSuccess(text);
        } catch (Exception e) {
            if (successExcToastEnable) {
                ToastUtil.showShort(activity, "请求数据完毕后处理异常：" + e);
            }
            e.printStackTrace();
            LogUtil.e("Http:===requestData====:" + e.toString());
        }
    }

    @Override
    public void onRequestError(Throwable e, HttpCallback httpCallback) {
        if (activity != null && showLoadingEnable) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).dismissDefaultLoadingView();
            }
        }
        if (failToastEnable) ToastUtil.showShort(activity, "请求异常：" + e);
        LogUtil.e("Http=====:" + e.toString());
        httpCallback.onFail(e);
    }
}
