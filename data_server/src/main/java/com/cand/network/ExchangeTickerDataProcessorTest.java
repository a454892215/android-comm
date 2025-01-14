package com.cand.network;

import com.cand.util.LogUtil;
import com.cand.util.ThreadU;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ExchangeTickerDataProcessorTest {

    @Test
    public void test() {
        try {
            ExchangeTickerDataServer server = new ExchangeTickerDataServer();
            server.connection(WebSocketConfig.SERVICE_URL + "/ws/v5/public");

            server.setOnOpeOkListener(() -> {
                ThreadU.sleep(1000);
                ArrayList<Map<String, String>> channelList = new ArrayList<>();
                Map<String, String> map = new HashMap<>();
                map.put("channel", "trades");
                map.put("instId", "EOS-USDT-SWAP");
                Map<String, String> map1 = new HashMap<>();
                map1.put("channel", "trades");
                map1.put("instId", "SOL-USD-SWAP");
                channelList.add(map);
                channelList.add(map1);
                server.subscribe(channelList);
            });
            Thread.sleep(1000 * 60 * 60 * 24 * 36500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
