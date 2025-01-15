package com.cand.util;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalUTest {

    @Test
    public void testGetFd(){
        BigDecimal start = new BigDecimal("100");
        BigDecimal end = new BigDecimal("80");
        double result = BigDecimalU.getFd(start, end);
        System.out.println("百分比幅度: " + result + "%");
    }
}
