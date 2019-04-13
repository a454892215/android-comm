package com.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.common.R;

import java.util.List;
import java.util.Map;

/**
 * Author:  L
 * CreateDate: 2019/1/5 9:46
 */
@SuppressWarnings("unused")
public class DataHandler_temple extends DataHandler {
    public DataHandler_temple(Context context) {
        super(context);
        isRemovePrefix_0 = false;
    }

    @Override
    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        String[] keys_1 = {"", "kd", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
        String[] headRightTitle = {"号码", "度量", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        int[] rightWidths = getTableRightWidthList();
        return null;
    }

    private int[] getTableRightWidthList() {
        int width = Math.round(context.getResources().getDimension(R.dimen.dp_100));
        int width_1 = Math.round(context.getResources().getDimension(R.dimen.dp_40));
        int width_2 = Math.round(context.getResources().getDimension(R.dimen.dp_31));
        return new int[]{width, width_1, width_2, width_2, width_2, width_2, width_2,
                width_2, width_2, width_2, width_2, width_2, width_2};
    }


    @Override
    protected SpannableStringBuilder getSpan(int position, String text) {
        if (position > 1 && !TextUtils.isEmpty(text)) {
            text = text.trim();
            text = " " + text + " ";
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
             builder.setSpan(new BgSpan( Color.RED, Color.WHITE), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }

}
