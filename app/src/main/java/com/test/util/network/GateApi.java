package com.test.util.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface GateApi {

    String baseUrl = " https://data.gateio.co";
    String commPath = "/api2/1/";

    @GET(commPath + "pairs")
    Observable<ResponseBody> getPairs();

    @GET(commPath + "marketinfo")
    Observable<ResponseBody> getMarketInfo();

    @GET(commPath + "candlestick2/btc_usdt?group_set=600&range_hour=12")
    Observable<ResponseBody> getCandleStick2();
}
