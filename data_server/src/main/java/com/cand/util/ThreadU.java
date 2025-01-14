package com.cand.util;

public class ThreadU {

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
