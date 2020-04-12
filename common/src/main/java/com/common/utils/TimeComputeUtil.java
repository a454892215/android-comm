package com.common.utils;

/**
 * Author:  Pan
 * CreateDate: 2019/8/13 9:08
 * Description: 把毫秒数，计算成，天，小时，分钟，秒
 */
@SuppressWarnings("unused")
public class TimeComputeUtil {

    private long days;

    private long hours;

    private long minutes;

    private long seconds;

    public void compute(long milliseconds) {
        long allSeconds = milliseconds / 1000;
        int all_seconds_day = 60 * 60 * 24; // 一天的秒数
        int all_seconds_hour = 60 * 60; // 一小时的秒数
        int all_seconds_minute = 60; // 一分钟的秒数

        days = allSeconds / all_seconds_day;
        hours = (allSeconds - days * all_seconds_day) / all_seconds_hour;
        minutes = (allSeconds - days * all_seconds_day - hours * all_seconds_hour) / all_seconds_minute;
        seconds = allSeconds - days * all_seconds_day - hours * all_seconds_hour - minutes * all_seconds_minute;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public String getSecondsStr() {
        return StringUtil.addZeroOnLessTen(seconds);
    }

    public String getMinutesStr() {
        return StringUtil.addZeroOnLessTen(minutes);
    }

    public String getHourStr() {
        return StringUtil.addZeroOnLessTen(hours);
    }

    public String getFormatTime() {
        return days + "天" + getHourStr() + "小时" + getMinutesStr() + "分" + getSecondsStr() + "秒";
    }

}
