package com.okex.open.api.test.ws.publicChannel;

import com.okex.open.api.test.ws.publicChannel.config.WebSocketClient;
import com.okex.open.api.test.ws.publicChannel.config.WebSocketConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

    // private static Logger logger = Logger.getLogger(PublicChannelTest.class);
    @Before
    public void connect() {
        //与服务器建立连接
        WebSocketConfig.loginConnect(webSocketClient);
    }

    @After
    public void close() {
        System.out.println(Instant.now().toString() + "Public channels close connect!");
        WebSocketClient.closeConnection();
    }

    /**
     * 行情频道
     */
    @Test
    public void tickersChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map spotTickerMap = new HashMap();
        spotTickerMap.put("channel", "tickers");
        spotTickerMap.put("instId", "FIL-USD-SWAP");
        channelList.add(spotTickerMap);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * K线频道
     */
    @Test
    public void candleChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "candle5m");
        map.put("instId", "BTC-USDT-210924");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 公共-指数K线频道
     * Index Candlesticks Channel
     */
    @Test
    public void indexCandleChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "index-candle30m");
        map.put("instId", "BTC-USDT");

        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 公共-指数行情频道
     */
    @Test
    public void indexTickersChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "index-tickers");
        map.put("instId", "BTC-USDT");

        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 公共-产品频道
     */
    @Test
    public void instrumentsChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "instruments");
        map.put("instType", "SPOT");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 持仓总量频道
     */
    @Test
    public void openInterestChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "open-interest");
        map.put("instId", "BTC-USDT-210924");
        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 交易频道
     */
    @Test
    public void tradesChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "trades");
        map.put("instId", "BTC-USDT-210625");
        Map map1 = new HashMap();
        map1.put("channel", "trades");
        map1.put("instId", "BTC-USD-210625");
        channelList.add(map);
        channelList.add(map1);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }


    /**
     * 预估交割/行权价格频道
     */
    @Test
    public void estimatedPriceChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "estimated-price");
        map.put("instType", "OPTION");
        map.put("instFamily", "BTC-USD");
        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }


    /**
     * 标记价格频道
     * Mark Price Channel
     */
    @Test
    public void markPriceChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "mark-price");
        map.put("instId", "BTC-USDT-210326");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 标记价格K线频道
     */
    @Test
    public void markPriceCandleChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "mark-price-candle1m");
        map.put("instId", "BTC-USD-210326");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 限价频道
     */
    @Test
    public void priceLimitChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "price-limit");
        map.put("instId", "BTC-USDT-210326");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 公共-深度频道(5档)
     */
    @Test
    public void books5Channel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "books5");
        map.put("instId", "BTC-USDT");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 公共-深度频道(400档)
     */
    @Test
    public void booksChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "books");
        map.put("instId", "BTC-USDT");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 公共-深度频道(400档增量)
     */
    @Test
    public void booksl2tbtChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "books-l2-tbt");
        map.put("instId", "BTC-USDT-SWAP");
        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 公共-深度频道(50档增量)
     */
    @Test
    public void books50l2tbtChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "books50-l2-tbt");
        map.put("instId", "BTC-USDT-SWAP");

        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 公共-深度频道(买卖一档)
     * 新增bbo深度频道，实时推送买卖1档深度数据
     * Order bbo-tbt Channel
     */
    @Test
    public void bbotbtChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "bbo-tbt");
        map.put("instId", "BTC-USDT");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }


    /**
     * 公共-期权定价频道
     * OPTION Summary Channel
     */
    @Test
    public void optSummaryChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "opt-summary");
        map.put("instFamily", "BTC-USD");
        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 公共-资金费率频道
     */
    @Test
    public void fundingRateChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "funding-rate");
        map.put("instId", "BTC-USDT-SWAP");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }


    /**
     * Status 频道
     */
    @Test
    public void statusChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "status");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 公共大宗交易频道
     * Public structure block trades channel
     */
    @Test
    public void publicStrucBlockTradesChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "public-struc-block-trades");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 大宗交易行情频道
     */
    @Test
    public void blocktickersChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "block-tickers");
        map.put("instId", "BTC-USDT");

        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 期权公共成交频道
     */
    @Test
    public void optionTradesChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "option-trades");
        map.put("instType", "OPTION");
        map.put("instId", "BTC-USD-230303-17000-C");
        map.put("instFamily", "BTC-USD");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 平台公共爆仓单频道
     */
    @Test
    public void liquidationOrdersChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "liquidation-orders");
        map.put("instType", "SWAP");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 价差撮合深度频道
     * sprd-bbo-tbt: 首次推1档快照数据，以后定量推送，每10毫秒当1档快照数据有变化推送一次1档数据
     * sprd-books5: 首次推5档快照数据，以后定量推送，每100毫秒当5档快照数据有变化推送一次5档数据
     */
    @Test
    public void sprdBooksChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "sprd-bbo-tbt");
        map.put("sprdId", "");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 价差撮合公共成交数据频道
     */
    @Test
    public void sprdPublicTradesChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "sprd-public-trades");
        map.put("sprdId", "BTC-USDT_BTC-USDT-SWAP");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 价差撮合行情频道
     */
    @Test
    public void sprdTickersChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "sprd-tickers");
        map.put("sprdId", "BTC-USDT_BTC-USDT-SWAP");


        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    /**
     * 全部交易频道
     */
    @Test
    public void tradesAllChannel() {
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "trades-all");
        map.put("instId", "BTC-USDT-SWAP");
        channelList.add(map);
        WebSocketClient.subscribe(channelList);
        sleepThread(10000000);
    }

    /**
     * 经济日历频道
     * 该频道使用如下服务地址
     * 生产环境 wss://ws.okx.com:8443/ws/v5/business，wss://wsaws.okx.com:8443/ws/v5/business
     * 该接口需验证后使用。仅支持实盘服务。
     */
    @Test
    public void economicCalendar() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "economic-calendar");
        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    private static void sleepThread(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动减仓预警频道
     * 该频道为公共频道，无需验证即可使用
     */
    @Test
    public void adlWarning() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();

        Map map = new HashMap();
        map.put("channel", "adl-warning");
        map.put("instType", "FUTURES");
        map.put("instFamily", "");

        channelList.add(map);
        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        sleepThread(10000000);
    }

    //取消订阅
    @Test
    public void unsubscribeChannel() {
        //添加订阅频道
        ArrayList<Map> channelList = new ArrayList<>();
        Map map = new HashMap();
        map.put("channel", "index-tickers");
        map.put("instId", "BTC-USDT");
        channelList.add(map);
        channelList.add(map);
        WebSocketClient.unsubscribe(channelList);
        //为保证收到服务端返回的消息，需要让线程延迟
        sleepThread(100);
    }


}
