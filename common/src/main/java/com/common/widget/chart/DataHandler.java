package com.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.common.R;

import java.util.ArrayList;
import java.util.List;

public abstract class DataHandler implements IDataHandler {
    protected int dp_1;

    private List<String> trendPointList = new ArrayList<>();

    DataHandler(Context context) {
        dp_1 = Math.round(context.getResources().getDimension(R.dimen.dp_1));
    }

    /**
     * @param dataArr 此数组length为4，每个数据依次对应表格左上->右上->左下->右下的数据
     * @return 处理后可直接给表格使用的数据
     */
    @SuppressWarnings("unused")
    @NonNull
    List<List<RowCell>> getResultData(List<List<String>> dataArr[]) {
        trendPointList.clear();
        List<List<RowCell>> resultList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {//左上->右上->左下->右下
            List<RowCell> list = new ArrayList<>();
            List<List<String>> partOfTableList = dataArr[i];
            for (int j = 0; j < partOfTableList.size(); j++) { // j 表示行号
                RowCell rowCell = getRowCell(partOfTableList.get(j), j, i);
                list.add(rowCell);
            }
            resultList.add(list);//左上->右上->左下->右下
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
            if ((type == 0 || type == 1) && rowIndex == 0) {
                entity.topLineStrokeSize = getLineStrokeSize(type, rowIndex);
            }
            if ((type == 0 || type == 2) && i == 0) {
                entity.leftLineStrokeSize = getLineStrokeSize(type, rowIndex);
            }
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
    public abstract SpannableStringBuilder getSpan(int type, int rowIndex, int position, String text);

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
