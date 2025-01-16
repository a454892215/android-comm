package com.cand.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateU {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getBeiJingTime(LocalDateTime dateTime){
        // 将 UTC 时间转换为北京时间
        ZonedDateTime beijingTime = dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        return beijingTime.format(FORMATTER);
    }
}
