package com.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;


import com.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataHandler implements IDataHandler {
    protected int dp_1;

    private List<String> trendPointList = new ArrayList<>();

    DataHandler(Context context) {
        dp_1 = Math.round(context.getResources().getDimension(R.dimen.dp_1));
    }

    @SuppressWarnings("unused")
    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        return null;
    }

    @SuppressWarnings("unused")
    @NonNull
    List<List<RowCell>> getResultData(List<String> dataArr[]) {
        trendPointList.clear();
        List<List<RowCell>> resultList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {//左上->右上->左上->右下
            List<RowCell> list = new ArrayList<>();
            for (int j = 0; j < dataArr.length; j++) { // j 表示行号
                RowCell rowCell = getRowCell(dataArr[j], j, i);
                list.add(rowCell);
            }
            resultList.add(list);
        }
        return resultList;
    }

    private RowCell getRowCell(List<String> names, int rowIndex, int type) {
        RowCell rowCell = new RowCell();
        rowCell.list = new ArrayList<>();
        rowCell.rowBgColor = getRowBColor(type, rowIndex);
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            RowCell.TableCellEntity entity = new RowCell.TableCellEntity(getCellWidth(type, rowIndex, i), getLineHeight(type, rowIndex));
            entity.lineColor = getLineColor(type, rowIndex);
            entity.lineStrokeSize = getLineStrokeSize(type, rowIndex);
            entity.span = getSpan(type, rowIndex, i, name);
            rowCell.list.add(entity);
        }
        return rowCell;
    }

    @SuppressWarnings("unused")
    public ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount) {
        ArrayList<CoordinateEntity> coordinateList = new ArrayList<>();
        int size = trendPointList.size();
        for (int position = 0; position < size; position++) {
            int column_value = Integer.parseInt(trendPointList.get(position));
            float x = 0; // right_widths[0] + (column_value) * right_widths[3] + right_widths[3] * 0.5f;
            float y = 0;// (currentPointCount + position) * cellHeight + cellHeight * 0.5f;
            coordinateList.add(new CoordinateEntity(x, y));
        }
        return coordinateList;
    }

    @Override
    public SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text) {
        return null;
    }

    @Override
    public int getCellWidth(int type, int rowIndex, int position) {
        return dp_1 * 50;
    }

    @Override
    public int getRowBColor(int type, int rowIndex) {
        return Color.TRANSPARENT;
    }

    @Override
    public int getLineColor(int type, int rowIndex) {
        return Color.GRAY;
    }

    @Override
    public int getLineStrokeSize(int type, int rowIndex) {
        return dp_1;
    }

    @Override
    public int getLineHeight(int type, int rowIndex) {
        return dp_1 * 35;
    }

}
