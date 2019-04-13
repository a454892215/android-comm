package com.common.widget.table;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;


import com.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:  L
 * CreateDate: 2019/1/5 9:46
 * Description: 走势图处理
 */

public class DataHandler {
    private int cellHeight;
    private Context context;
    private int dp_1;


    boolean isRemovePrefix_0 = false;
    List<String> trendPointList = new ArrayList<>();
    int[] right_widths;

    DataHandler(Context context) {
        this.context = context;
        dp_1 = Math.round(context.getResources().getDimension(R.dimen.dp_1));
        cellHeight = dp_1 * 35;
    }

    @SuppressWarnings("unused")
    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        return null;
    }

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
        rowCell.rowBgColor = getRowBColor(type,rowIndex);
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            RowCell.TableCellEntity entity = new RowCell.TableCellEntity(getWidth(type,rowIndex), cellHeight);
            entity.lineColor = getLineColor(type,rowIndex);
            entity.lineStrokeSize = getLineStrokeSize(type,rowIndex);
            entity.span = getSpan(i, name);
            rowCell.list.add(entity);
        }
        return rowCell;
    }

    public ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount) {
        ArrayList<CoordinateEntity> coordinateList = new ArrayList<>();
        int size = trendPointList.size();
        for (int position = 0; position < size; position++) {
            int column_value = Integer.parseInt(trendPointList.get(position));
            float x = right_widths[0] + (column_value) * right_widths[3] + right_widths[3] * 0.5f;
            float y = (currentPointCount + position) * cellHeight + cellHeight * 0.5f;
            coordinateList.add(new CoordinateEntity(x, y));
        }
        return coordinateList;
    }

    protected SpannableStringBuilder getSpan(int position, String text) {
        return null;
    }

    protected int getWidth(int type, int rowIndex) {
        return 100;
    }

    protected int getRowBColor(int type, int rowIndex) {
        return 100;
    }

    protected int getLineColor(int type, int rowIndex) {
        return 100;
    }
    protected int getLineStrokeSize(int type, int rowIndex) {
        return 100;
    }
}
