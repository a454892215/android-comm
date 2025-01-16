package com.cand.server;

import org.junit.Before;
import org.junit.Test;


public class StartTest {
    @Before
    public void setBefore() {
    }

    @Test
    public void test() {
        TickerDataServerStart start = new TickerDataServerStart();
        start.start();
    }

}
