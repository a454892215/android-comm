package com.test.util.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface JvHeApi {

    String baseUrl = "http://v.juhe.cn";

    String commPath = "?&key=5ac63094e99467342f086967b71c2f5e";


    @GET("toutiao/index" + commPath + "&type=top")
    Observable<ResponseBody> getTouTiao();
}
