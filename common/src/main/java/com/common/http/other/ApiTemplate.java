package com.common.http.other;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Author:  L
 * CreateDate: 2019/1/18 8:58
 * Description: No
 */

public interface ApiTemplate {
    /**
     * Get请求示例1： 无参数示例
     */
    @GET("config")
    Observable<ResponseBody> getConfigObservable();

    /**
     * Get请求示例2：  String参数示例
     */
    @GET("member/detail/func")
    Observable<ResponseBody> getRefundObservable(@Query("execType") String execType);

    /**
     * Get请求示例3：   Map参数 加请求头 示例
     */
    @GET("captcha")
    @Headers({"Accept: application/x.tg.v2+json",})
    Observable<ResponseBody> getVerifyCoder(@QueryMap Map<String, String> params);


    /**
     * Get请求示例4：   int+Map参数示例 并且添加请求头
     */
    @GET("member/detail/method")
    @Headers({"Accept: application/x.tg.v2+json",})
    Observable<ResponseBody> getBonusObservable(@Query("page") int page, @QueryMap Map<String, String> params);


    /**
     * Post请求示例1：   Map参数示例
     */
    @POST("path/num")
    Observable<ResponseBody> getNumObservable(@Body Map<String, String> params);

    /**
     * Post请求示例2：   Map参数 加上请求头 示例
     */
    @POST("member/set-bank-info")
    @Headers({"device: android",})
    Observable<ResponseBody> getAddBankInfoObservable(@Body Map<String, String> params);

    /**
     * Post请求示例3：   int + Map参数 加上请求头 示例
     */
    @POST("agency/lowerList")
    @Headers({"Accept: application/x.tg.v2+json",})
    Observable<ResponseBody> getLowerListObservable(@Query("page") int pager, @Body Map<String, String> params);

}
