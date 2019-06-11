package com.common.http;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.common.base.BaseActivity;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.utils.ToastUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@SuppressWarnings("unused")
public class HttpUtil {

    protected Activity activity;

    private boolean showLoadingEnable;
    private boolean checkNetworkEnable = true;
    private boolean successExcToastEnable = true;
    private boolean failToastEnable = true;

    public HttpUtil(Activity activity) {
        this.activity = activity;
    }

    public HttpUtil successExcToastEnable(boolean enable) {
        this.successExcToastEnable = enable;
        return this;
    }

    public HttpUtil failToastEnable(boolean enable) {
        this.failToastEnable = enable;
        return this;
    }

    /**
     * 此功能只有 传入的activity继承Common中的BaseActivity才生效
     */
    public HttpUtil showLoadingEnable(boolean enable) {
        this.showLoadingEnable = enable;
        return this;
    }

    public HttpUtil checkNetworkEnable(boolean enable) {
        this.checkNetworkEnable = enable;
        return this;
    }

    public void requestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        if (checkNetworkEnable) {
            if (!SystemUtils.isNetWorkConnected(activity)) {
                ToastUtil.showShort(activity, "请检测网络是否连接");
                return;
            }
        }
        startRequestData(observable, httpCallback);
    }


    @SuppressLint("CheckResult")
    protected void startRequestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        if (activity != null && showLoadingEnable) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showDefaultLoadingView();
            }
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> onRequestSuccess(responseBody, httpCallback),
                        throwable -> onRequestError(throwable, httpCallback));
    }


    protected void onRequestSuccess(ResponseBody responseBody, HttpCallback httpCallback) {
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

    protected void onRequestError(Throwable e, HttpCallback httpCallback) {
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
