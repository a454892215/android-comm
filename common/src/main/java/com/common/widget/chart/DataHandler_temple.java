package com.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class DataHandler_temple extends DataHandler {
    public DataHandler_temple(Context context) {
        super(context);
    }

    @Override
    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        String[] keys_1 = {"", "kd", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
        String[] headRightTitle = {"号码", "度量", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        int[] rightWidths = getTableRightWidthList();
        return null;
    }

    private int[] getTableRightWidthList() {
        int width = dp_1 * 100;
        int width_1 = dp_1 * 40;
        int width_2 = dp_1 * 30;
        return new int[]{width, width_1, width_2, width_2, width_2, width_2, width_2,
                width_2, width_2, width_2, width_2, width_2, width_2};
    }


    @Override
    public SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text) {
        if (position > 1 && !TextUtils.isEmpty(text)) {
            text = text.trim();
            text = " " + text + " ";
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new BgSpan(Color.RED, Color.WHITE), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }
}
