package com.common.widget.chart;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.List;

public interface IDataHandler {


    List<List<RowCell>> handleData();
    ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount);

    SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text);

    int getCellWidth(int type, int rowIndex, int position);

    int getRowBColor(int type, int rowIndex);

    int getLineColor(int type, int rowIndex);

    int getLineStrokeSize(int type, int rowIndex);

    int getLineHeight(int type, int rowIndex);
}
