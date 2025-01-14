package com.okex.open.api.test.ws.publicChannel;

import com.cand.network.http.HttpUtil;
import com.google.gson.Gson;

public class OkxModel {

    public TickersEntity getTickers() {
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.get(OkxApi.baseUrl + OkxApi.tickers + "?instType=SWAP");
        Gson gson = new Gson();
        return gson.fromJson(result, TickersEntity.class);
    }
}
