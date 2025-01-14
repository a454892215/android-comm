package com.okex.open.api.test.ws.publicChannel.config;
@SuppressWarnings({"unused"})
public class WebSocketConfig {
    /**
     实盘交易
     实盘API交易地址如下：

     REST：https://www.okx.com
     WebSocket公共频道：wss://ws.okx.com:8443/ws/v5/public
     WebSocket私有频道：wss://ws.okx.com:8443/ws/v5/private
     WebSocket业务频道：wss://ws.okx.com:8443/ws/v5/business
     AWS 地址如下：

     REST：https://aws.okx.com
     WebSocket公共频道：wss://wsaws.okx.com:8443/ws/v5/public
     WebSocket私有频道：wss://wsaws.okx.com:8443/ws/v5/private
     WebSocket业务频道：wss://wsaws.okx.com:8443/ws/v5/business
     模拟盘交易
     目前可以进行V5 API的模拟盘交易，部分功能不支持如提币、充值、申购赎回等。

     模拟盘API交易地址如下：

     REST：https://www.okx.com
     WebSocket公共频道：wss://wspap.okx.com:8443/ws/v5/public
     WebSocket私有频道：wss://wspap.okx.com:8443/ws/v5/private
     WebSocket业务频道：wss://wspap.okx.com:8443/ws/v5/business
     */
    //ws 模拟盘 the demo trading URL
  //  private static final String SERVICE_URL = "wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999";
    //ws 实盘 the live trading URL
    private static final String SERVICE_URL = "wss://ws.okx.com:8443/ws/v5/public";
    //部分订阅频道已迁移
   // private static final String SERVICE_URL = "wss://ws.okx.com:8443/ws/v5/business";
    // api key
    private static final String API_KEY = "";
    private static final String SECRET_KEY = "";
    private static final String PASSPHRASE = "";

    public static void publicConnect(WebSocketClient webSocketClient) {
        System.out.println(SERVICE_URL);
        WebSocketClient.connection(SERVICE_URL);
    }

    public static void loginConnect(WebSocketClient webSocketClient) {
        System.out.println(SERVICE_URL);
        //与服务器建立连接
        WebSocketClient.connection(SERVICE_URL);
    }
}
