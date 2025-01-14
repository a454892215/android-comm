package com.okex.open.api.test.ws.publicChannel;

import com.cand.util.LogUtil;

import org.junit.Test;

public class OkxModelTest {

    @Test
    public void testGetTickers(){
        OkxModel okxModel = new OkxModel();
        TickersEntity tickers = okxModel.getTickers();
        for (int i = 0; i < tickers.data.size(); i++) {
            TickersEntity.Item datum = tickers.data.get(i);
            LogUtil.d2(i + " instId:" + datum.instId + " " + datum.last);
        }
    }
}
