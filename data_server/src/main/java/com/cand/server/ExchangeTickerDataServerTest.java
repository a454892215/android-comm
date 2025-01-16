package com.cand.server;

import org.junit.Before;
import org.junit.Test;


public class ExchangeTickerDataServerTest {
    @Before
    public void setBefore() {
    }

    @Test
    public void test() {
        TickerDataServerStart start = new TickerDataServerStart();
        start.start();
    }

}
