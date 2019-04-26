package com.common.http;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.utils.ToastUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtil implements IHttpUtil {

    protected BaseActivity activity;

    private boolean showLoadingEnable;
    private boolean checkNetworkEnable = true;
    private boolean successExcToastEnable = true;
    private boolean failToastEnable = true;

    public HttpUtil(BaseActivity activity) {
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


    public HttpUtil showLoadingEnable(boolean enable) {
        this.showLoadingEnable = enable;
        return this;
    }


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


    private void startRequestData(Observable<ResponseBody> observable, HttpCallback httpCallback) {
        if (activity != null && showLoadingEnable) activity.showDefaultLoadingView();
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
                activity.dismissDefaultLoadingView();
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
            activity.dismissDefaultLoadingView();
        }
        if (failToastEnable) ToastUtil.showShort(activity, "请求异常：" + e);
        LogUtil.e("Http=====:" + e.toString());
        httpCallback.onFail(e);
    }
}
