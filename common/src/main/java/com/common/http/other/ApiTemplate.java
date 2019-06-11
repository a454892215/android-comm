package com.common.http.other;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
