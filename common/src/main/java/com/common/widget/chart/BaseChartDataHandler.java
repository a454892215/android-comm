package com.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;

import com.common.R;
import com.common.widget.view_func.BgTextShape;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseChartDataHandler implements IDataHandler {
    protected float dp_1;

    private List<Pair<Integer, Integer>> trendLinePointList = new ArrayList<>();//第三个RV曲线节点列号列表,行号
    private int cellHeight;

    protected BaseChartDataHandler(Context context) {
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        cellHeight = Math.round(dp_1 * 35);
    }

    /**
     * @param dataArr 此数组length为4，每个数据依次对应表格左上->右上->左下->右下的数据
     * @return 处理后可直接给表格使用的数据
     */
    @SuppressWarnings("unused")
    protected List<List<RowCell>> getResultData(List<List<String>>[] dataArr) {
        if (dataArr == null) return null;
        trendLinePointList.clear();
        List<List<RowCell>> resultList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {//左上->右上->左下->右下
            List<RowCell> list = new ArrayList<>();
            List<List<String>> partOfTableList = dataArr[i];
            int size = partOfTableList.size();
            for (int j = 0; j < size; j++) { // j 表示行号
                RowCell rowCell = getRowCell(partOfTableList.get(j), j, i, size);
                list.add(rowCell);
            }
            resultList.add(list);//左上->右上->左下->右下
        }
        return resultList;
    }

    private RowCell getRowCell(List<String> names, int rowIndex, int type, int totalRow) {
        RowCell rowCell = new RowCell();
        rowCell.list = new ArrayList<>();
        rowCell.rowBgColor = getRowBColor(type, rowIndex);
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            RowCell.TableCellEntity entity = new RowCell.TableCellEntity(getCellWidth(type, rowIndex, i), cellHeight);
            entity.lineColor = getLineColor(type, rowIndex);
            entity.rightLineStrokeSize = getLineStrokeSize(type, rowIndex);
            entity.bottomLineStrokeSize = getBottomLineStrokeSize(type, rowIndex, totalRow);
            if ((type == 0 || type == 1) && rowIndex == 0) {
                entity.topLineStrokeSize = getLineStrokeSize(type, rowIndex);
            }
            if ((type == 0 || type == 2) && i == 0) {
                entity.leftLineStrokeSize = getLineStrokeSize(type, rowIndex);
            }
            entity.textArr = getTextArr(type, rowIndex, i, name);
            entity.bgColorArr = getTextBgColor(type, rowIndex, i, name);
            entity.rectSize = getTextRectSize(type, rowIndex, i, name);
            entity.rectHeight = getTextRectHeight(type, rowIndex, i, name);
            entity.rectBgRadius = getTextRectBgRadius(type, rowIndex, i, name);
            entity.textColor = getTextColor(type, rowIndex, i, name);
            entity.textColors = getTextColors(type, rowIndex, i, name);
            entity.horizontalSpacing = getHorizontalSpacing(type, rowIndex, i, name);
            entity.bgTextShape = getBgTextShape(type, rowIndex, i, name);
            entity.strokeInfo = getStrokeInfo(type, rowIndex, i);
            if (type == 3) {
                addTrendLinePointList(trendLinePointList, rowIndex, i, name);
            }
            rowCell.list.add(entity);
        }
        return rowCell;
    }

    @Override
    public void addTrendLinePointList(List<Pair<Integer, Integer>> trendLinePointList, int rowIndex, int position, String text) {

    }

    @Override
    public ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount) {
        int size = trendLinePointList.size();
        if (size > 0) {
            ArrayList<CoordinateEntity> coordinateList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Pair<Integer, Integer> pair = trendLinePointList.get(i);
                int columnIndex = pair.first;
                int rowIndex = pair.second;
                float x = getCellCenterToLeftDistance(columnIndex); //width_2 + width_3 * 2 + (column - 3) * width_4 + width_4 * 0.5f;
                float y = (currentPointCount + rowIndex) * cellHeight + cellHeight * 0.5f;
                coordinateList.add(new CoordinateEntity(x, y));
            }
            return coordinateList;
        }
        return null;
    }

    @Override
    public float getCellCenterToLeftDistance(int column) {
        return 0;
    }

    @Override
    public Object[] getTextArr(int type, int rowIndex, int position, String text) {
        return null;
    }

    @Override
    public int[] getTextBgColor(int type, int rowIndex, int position, String text) {
        return null;
    }

    @Override
    public int getTextRectSize(int type, int rowIndex, int position, String text) {
        return Math.round(dp_1 * 22);
    }

    @Override
    public int getTextRectHeight(int type, int rowIndex, int position, String text) {
        return 0;
    }

    @Override
    public int getTextRectBgRadius(int type, int rowIndex, int position, String text) {
        return 0;
    }

    @Override
    public int getTextColor(int type, int rowIndex, int position, String text) {
        return 0;
    }

    @Override
    public int[] getTextColors(int type, int rowIndex, int position, String text) {
        return null;
    }

    @Override
    public int getHorizontalSpacing(int type, int rowIndex, int position, String text) {
        return Math.round(dp_1 * 2);
    }

    @Override
    public BgTextShape getBgTextShape(int type, int rowIndex, int position, String text) {
        return null;
    }

    @Override
    public int getCellWidth(int type, int rowIndex, int position) {
        return Math.round(dp_1 * 50);
    }

    @Override
    public int getRowBColor(int type, int rowIndex) {
        return Color.TRANSPARENT;
    }

    @Override
    public int getLineColor(int type, int rowIndex) {
        return 0xFFE4E4E4;
    }

    @Override
    public int getLineStrokeSize(int type, int rowIndex) {
        return Math.round(dp_1);
    }

    @Override
    public int getBottomLineStrokeSize(int type, int rowIndex, int totalRow) {
        return Math.round(dp_1);
    }

    @Override
    public RowCell.StrokeInfo getStrokeInfo(int type, int rowIndex, int position) {
        return null;
    }
}
