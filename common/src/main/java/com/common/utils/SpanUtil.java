package com.common.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * Author:  Pan
 * CreateDate: 2019/7/8 8:47
 * Description: No
 */

public class SpanUtil {

    public static SpannableString getTextColorSpan(String text, String selectedText, int Color) {
        SpannableString spannableString = new SpannableString(text);
        int start = text.indexOf(selectedText);
        spannableString.setSpan(
                new ForegroundColorSpan(Color),
                start, start + selectedText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
