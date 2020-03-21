package com.test.util.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OkexApi {
    String baseUrl = "https://www.okex.com";
  //  String baseUrl = "https://31.13.75.18";
    String commPath = "/v2/spot/instruments/";
    String commPath_v3 = "/api/spot/v3/instruments/";

    @GET(commPath_v3 + "BTC-USDT/candles")
    @Headers({"Content-Type: application/json",})
    Observable<ResponseBody> getBtcCandle(@Query("granularity") int granularity);

    //https://www.okex.com/v2/spot/instruments/BTC-USDT/candles?granularity=180&size=1000&t=1584782119121
    /**
     * 币币行情
     * @return 蜡烛图数据
     */
    @GET(commPath + "BTC-USDT/candles?granularity=180&size=1000&t=1584782119121")
    Observable<ResponseBody> getBtcCandle_old();
}
