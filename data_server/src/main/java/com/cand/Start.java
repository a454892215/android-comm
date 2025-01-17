package com.cand;

import com.cand.server.TickerDataServerStart;
import com.cand.util.LogUtil;
import com.cand.util.ThreadU;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Start {
    public static void main(String[] args) {
        waitForStopSignal();
    }

    public static void waitForStopSignal() {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("输入 1. tickers任务 ：");
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if ("1".equalsIgnoreCase(input.trim())) {
                        LogUtil.d2("1. 准备开始tickers任务:" + input);
                        TickerDataServerStart start = new TickerDataServerStart();
                        start.start();
                    } else {
                        LogUtil.d2("非法输入，不处理：" + input);
                    }
                }
                LogUtil.d("======start 输入接收器 结束========");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }
}
