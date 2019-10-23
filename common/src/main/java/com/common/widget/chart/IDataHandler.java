package com.common.widget.chart;

import android.util.Pair;

import com.common.widget.view_func.BgTextShape;

import java.util.ArrayList;
import java.util.List;

public interface IDataHandler {


    List<List<RowCell>> handleData();

    /**
     * @param currentPointCount 现在节点的个数
     * @return 曲线节点坐标集合
     */
    ArrayList<CoordinateEntity> computeCoordinate(int currentPointCount);

    int getCellWidth(int type, int rowIndex, int position);

    int getRowBColor(int type, int rowIndex);

    int getLineColor(int type, int rowIndex);

    int getLineStrokeSize(int type, int rowIndex);

    int getBottomLineStrokeSize(int type, int rowIndex, int totalRow);

    Object[] getTextArr(int type, int rowIndex, int position, String text);

    int[] getTextBgColor(int type, int rowIndex, int position, String text);

    int getTextRectSize(int type, int rowIndex, int position, String text);

    int getTextRectHeight(int type, int rowIndex, int position, String text);

    int getTextRectBgRadius(int type, int rowIndex, int position, String text);

    int getTextColor(int type, int rowIndex, int position, String text);

    int[] getTextColors(int type, int rowIndex, int position, String text);

    int getHorizontalSpacing(int type, int rowIndex, int position, String text);

    BgTextShape getBgTextShape(int type, int rowIndex, int position, String text);

    /**
     *
     * @param trendLinePointList 曲线节点的列号 行号
     * @param position 位置
     * @param text cell中要显示的主的文本
     */
    void addTrendLinePointList(List<Pair<Integer, Integer>> trendLinePointList, int rowIndex, int position, String text);

    float getCellCenterToLeftDistance(int column);

    RowCell.StrokeInfo getStrokeInfo(int type, int rowIndex, int position);

}
