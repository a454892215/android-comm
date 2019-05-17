package com.test.util.cryptocurrency;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

public interface Api {

    String baseUrl = " https://data.gateio.co";

    @GET("/api2/1/pairs")
    Observable<ResponseBody> getPairs();
}
