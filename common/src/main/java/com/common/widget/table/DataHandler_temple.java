package com.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:  L
 * CreateDate: 2019/1/5 9:46
 */
@SuppressWarnings("unused")
public class DataHandler_temple extends DataHandlerOfNumTrend {
    public DataHandler_temple(Context context) {
        super(context);
        isRemovePrefix_0 = false;
    }

    @Override
    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        String[] keys_1 = {key_resultInfo, "kd", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
        String[] names_1 = {"号码", "度量", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        int[] rightWidths = getTableRightWidthList();
        return getResultData(list, keys_1, names_1, rightWidths);
    }

    private int[] getTableRightWidthList() {
        int width = Math.round(context.getResources().getDimension(R.dimen.dp_100));
        int width_1 = Math.round(context.getResources().getDimension(R.dimen.dp_40));
        int width_2 = Math.round(context.getResources().getDimension(R.dimen.dp_31));
        return new int[]{width, width_1, width_2, width_2, width_2, width_2, width_2,
                width_2, width_2, width_2, width_2, width_2, width_2};
    }

    @Override
    protected int getTextColor(int position) {
        switch (position) {
            case 0:
                return chart_red;
        }
        return text_color_1;
    }

    @Override
    public ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount) {
        ArrayList<CoordinateEntity> coordinateList = new ArrayList<>();
        int size = trendPointList.size();
        for (int position = 0; position < size; position++) {
            int column_value = Integer.parseInt(trendPointList.get(position));
            float x = right_widths[0] + right_widths[1] + (column_value - 1) * right_widths[3] + right_widths[3] * 0.5f;
            float y = (currentPointCount + position) * cellHeight + cellHeight * 0.5f;
            coordinateList.add(new CoordinateEntity(x, y));
        }
        return coordinateList;
    }

    @Override
    protected SpannableStringBuilder getSpan(int position, String text) {
        if (position > 1 && !TextUtils.isEmpty(text)) {
            text = text.trim();
            text = " " + text + " ";
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new RoundBackgroundColorSpan(chart_red, Color.WHITE, roundBgRadius), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }

}
