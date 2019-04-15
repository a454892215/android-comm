package com.common.widget.table;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IDataHandler {
    List<List<RowCell>> handleData(List<Map<String, String>> list);

    ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount);

    SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text);

    int getCellWidth(int type, int rowIndex, int position);

    int getRowBColor(int type, int rowIndex);

    int getLineColor(int type, int rowIndex);

    int getLineStrokeSize(int type, int rowIndex);

    int getLineHeight(int type, int rowIndex);
}
