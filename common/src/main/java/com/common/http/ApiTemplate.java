package com.common.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:  L
 * CreateDate: 2019/1/18 8:58
 * Description: No
 */

public interface ApiTemplate {
    @POST("path/num")
    Observable<ResponseBody> getNumObservable(@Body Map<String, String> params);

    @POST("path/size")
    Observable<ResponseBody> getSizeObservable(@Body Map<String, String> params);
}
