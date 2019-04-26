package com.common.http;

import okhttp3.ResponseBody;
import rx.Observable;

@SuppressWarnings("unused")
public interface IHttpUtil {

    HttpUtil showLoadingEnable(boolean isShowLoading);

    HttpUtil checkNetworkEnable(boolean enable);

    HttpUtil successExcToastEnable(boolean enable);

    HttpUtil failToastEnable(boolean enable);

    void requestData(Observable<ResponseBody> observable, HttpCallback httpCallback);

    void onRequestError(Throwable e, HttpCallback httpCallback);

    void onRequestSuccess(ResponseBody responseBody, HttpCallback httpCallback);
}
