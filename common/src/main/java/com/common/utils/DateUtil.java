package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author:  L
 * CreateDate: 2018/11/22 15:54
 * Description: No
 */

@SuppressWarnings("unused")
public class DateUtil {
    /**
     * 获取当前日期是一周的第几天 比如：周一是一周的第一天  周二是一周的第二天
     *
     * @return 当前日期是星期几
     */
    private static int getIndexOfWeek(Date date) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6}; //{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取格式化年月日
     *
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String getFormatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String getFormatDateByPhpTime(long date) {
        if (String.valueOf(date).length() == 10) { //php和java时间戳相差3位
            date = date * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
        return format.format(date);
    }

    public static String getHourAndMinute(long date) {
        SimpleDateFormat format = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        return format.format(date);
    }


    public static String getDateOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_YEAR);
        int dayIndex_current = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if (dayIndex_current - dayIndex == 0) {
            return "今天";
        } else if (dayIndex_current - dayIndex == 1) {
            return "昨天";
        } else if (dayIndex_current - dayIndex == 2) {
            return "前天";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    private static String[] time_of_day_1 = {"凌晨", "上午", "中午", "下午", "傍晚", "晚上"};
    private static int[] time_of_day_2 = {6, 12, 14, 18, 20, 24};

    public static String getTimeOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // LogUtil.d("===getTimeOfDay=== hour:" + hour);
        for (int i = 0; i < time_of_day_2.length; i++) {
            if (hour < time_of_day_2[i]) return time_of_day_1[i];
        }
        return "";
    }

    /**
     * 获取时间戳对应的年月日 时分秒
     */
    public static String getLongTime(Long value) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(value));
    }

    /**
     * 获取格式化年月日
     *
     * @return yyyy-MM-dd
     */
    public static String getFileNameFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yy年MM月dd日_HH时mm分ss秒", Locale.getDefault());
        return format.format(date);
    }
}
