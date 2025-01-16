package com.okex.open.api.test.ws.publicChannel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
public class TradeChannelSubscribeEntity {
    public String op = "subscribe";
    public List<Item> args = new ArrayList<>();

    public static class Item {
        public String instId;
        public String channel = "trades";

        public Item(String instId) {
            this.instId = instId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(instId, item.instId) && Objects.equals(channel, item.channel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instId, channel);
        }
    }

    /**
     * 对比和另一个TradeChannelSubscribeEntity对象是否相同，不需要两个args中的元素顺序也相同，只需要每个元素可以一一对应相同就可以判断相同
     */
    public boolean equals(TradeChannelSubscribeEntity other) {
        if (other == null) return false;

        Set<Item> set1 = new HashSet<>(other.args);
        Set<Item> set2 = new HashSet<>(this.args);
        return set1.equals(set2);
    }

    public static void main(String[] args) {
        TradeChannelSubscribeEntity entity1 = new TradeChannelSubscribeEntity();
        entity1.args.add(new Item("BTC-USD"));
        entity1.args.add(new Item("ETH-USD"));

        TradeChannelSubscribeEntity entity2 = new TradeChannelSubscribeEntity();
        entity2.args.add(new Item("ETH-USD"));
        entity2.args.add(new Item("BTC-USD"));

        boolean isEqual = entity1.equals(entity2);
        System.out.println("是否相同? " + isEqual);
    }
}

