package com.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import com.common.utils.CastUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DataHandler_temple extends DataHandler {
    public DataHandler_temple(Context context) {
        super(context);
    }

    public List<List<RowCell>> handleData() {
        String arr[] = new String[6];
        List<List<String>> dataArr[] = CastUtil.cast(new ArrayList[4]);
        dataArr[0] = getTestData(1, 1);
        dataArr[1] = getTestData(1, 10);
        dataArr[2] = getTestData(100, 1);
        dataArr[3] = getTestData(100, 10);
        return getResultData(dataArr);
    }


    @Override
    public SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text) {
        text = text.trim();
        text = text + " ";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int textColor = type != 3 ? Color.BLACK : Color.parseColor("#333333");
        builder.setSpan(BgSpan.getSpan(textColor, Color.TRANSPARENT), 0, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private List<List<String>> getTestData(int row, int column) {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < row; i++) {//行数
            List<String> rowList = new ArrayList<>();
            for (int j = 0; j < column; j++) { //列数
                rowList.add("行号" + i + " 列号" + j);
            }
            list.add(rowList);
        }
        return list;
    }

    @Override
    public int getCellWidth(int type, int rowIndex, int position) {
        return dp_1 * 80;
    }
}
