package com.okex.open.api.test.ws.publicChannel;

import com.cand.network.http.HttpUtil;
import com.cand.util.LogUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OkxApiModel {

    public TickersEntity getTickers() {
        TickersEntity tickersEntity = new TickersEntity();
        tickersEntity.data = new ArrayList<>();
        String result = "";
        try {
            tickersEntity = null;
            HttpUtil httpUtil = new HttpUtil();
            result = httpUtil.get(OkxApi.baseUrl + OkxApi.tickers + "?instType=SWAP");
            LogUtil.d(result);
            Gson gson = new Gson();
            tickersEntity = gson.fromJson(result, TickersEntity.class);
        } catch (Exception e) {
            LogUtil.d("发生异常 result："+  result +  " E:" + e);
        }
        return tickersEntity;
    }
}
