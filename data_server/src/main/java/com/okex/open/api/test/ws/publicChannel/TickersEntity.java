package com.okex.open.api.test.ws.publicChannel;

import java.util.List;

public class TickersEntity {
    public List<Item> data;

    public class Item {
        public String instId;
        public String last;
    }
}
