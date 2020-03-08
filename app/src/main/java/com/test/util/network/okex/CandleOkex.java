package com.test.util.network.okex;

import java.util.List;

/**
 * Author: Pan
 * 2020/3/8
 * Description:
 */
public class CandleOkex {
   public String code;

   /**
    *
    * 最后一天数据 除了开盘价格确定 最高价，最低价 收盘价不确定
    *     [
    *             "2020-03-07T16:00:00.000Z",
    *             "9114.7",//开盘价
    *             "9188.6", //最高价
    *             "8701.9", //最低价
    *             "8745.7",
    *             "27480.64639307"
    *         ]
    */
   public List<List<String>> data;
   public String detailMsg;
   public String error_code;
   public String error_message;
   public String msg;


}
