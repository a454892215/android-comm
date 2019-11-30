package com.common.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Author:  Pan
 * CreateDate: 2019/7/8 8:47
 * Description: No
 */
@SuppressWarnings("unused")
public class SpanUtil {

    /**
     * 适用于selectedText 出现在text第一个位置
     */
    public static SpannableString getTextColorSpan(String text, String selectedText, int Color) {
        SpannableString spannableString = new SpannableString(text);
        int start = text.indexOf(selectedText);
        spannableString.setSpan(
                new ForegroundColorSpan(Color),
                start, start + selectedText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * selectedText1 出现在text第一个位置
     * selectedText2 出现在text第一个位置
     */
    public static SpannableStringBuilder getTextColorSpan(String text, String selectedText1,
                                                          int Color1, String selectedText2, int Color2) {
        SpannableStringBuilder spnBuilder = new SpannableStringBuilder(text);
        int start = text.indexOf(selectedText1);
        spnBuilder.setSpan(new ForegroundColorSpan(Color1), start, start + selectedText1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int start2 = selectedText1.equals(selectedText2) ? text.indexOf(selectedText2, 1) : text.indexOf(selectedText2);
        spnBuilder.setSpan(new ForegroundColorSpan(Color2), start2, start2 + selectedText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spnBuilder;
    }
}
