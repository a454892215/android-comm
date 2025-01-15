package com.okex.open.api.test.ws.publicChannel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TradeChannelSubscribeEntity {
    public String op = "subscribe";
    public List<Item> args = new ArrayList<>();

    public static class Item{
        public String instId;
        public String channel = "trades";

        public Item(String instId) {
            this.instId = instId;
        }

    }
}
