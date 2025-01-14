package com.okex.open.api.test.ws.publicChannel;

import com.cand.network.http.HttpUtil;
import com.cand.util.LogUtil;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.List;

public class OkxApiTest {

    @Test
    public void testTickersApi(){
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.get(OkxApi.baseUrl + OkxApi.tickers + "?instType=SWAP");
        Gson gson = new Gson();
        TickersEntity tickersEntity = gson.fromJson(result, TickersEntity.class);
        List<TickersEntity.Item> data = tickersEntity.data;
        for (int i = 0; i < data.size(); i++) {
            TickersEntity.Item datum = data.get(i);
            LogUtil.d2(i + " instId:" + datum.instId + " " + datum.last);
        }

        // LogUtil.d("result:" + result);
    }
}
