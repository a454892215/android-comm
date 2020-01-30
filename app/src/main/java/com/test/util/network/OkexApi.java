package com.test.util.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface OkexApi {

    String baseUrl = "https://www.okex.com";

    String commPath = "/v2/spot/instruments/";

    @GET(commPath + "BTC-USDT/candles?granularity=86400&size=1000&t=1580363812013")
    Observable<ResponseBody> getBtcCandle();
}
