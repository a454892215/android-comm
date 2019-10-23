package com.test.util.custom_view.fragment.other;

import com.common.utils.CastUtil;
import com.common.widget.chart.BaseChartDataHandler;
import com.common.widget.chart.RowCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChartDataHandler extends BaseChartDataHandler {

    private static final String[] leftTitles = {"期号"};
    private static final String[] rightTitles = {"标题1", "标题2", "标题3", "标题4", "标题5", "标题6"};
    private final int width_1;
    private final int width_2;
    private final int width_3;

    public ChartDataHandler() {
        width_1 = Math.round(dp_1 * 60);
        width_2 = Math.round(dp_1 * 140);
        width_3 = Math.round(dp_1 * 80);
    }

    private List<List<String>>[] getHandledData() {
        List<List<String>>[] dataArr = CastUtil.cast(new ArrayList[4]);
        dataArr[0] = getHeaderData(ChartDataHandler.leftTitles);
        dataArr[1] = getHeaderData(rightTitles);
        dataArr[2] = getBodyData(2);
        dataArr[3] = getBodyData(3);
        return dataArr;
    }


    private List<List<String>> getBodyData(int type) {
        int column = type == 2 ? 1 : rightTitles.length;
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) { //行数
            List<String> row = new ArrayList<>();
            for (int j = 0; j < column; j++) {//列数
                row.add("内容：" + j);
            }
            list.add(row);
        }
        return list;
    }

    private List<List<String>> getHeaderData(String[] titles) {
        List<List<String>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList(titles)));
        return list;
    }

    private int[] bgRowColors = {0xFFF8F8F8, 0xffffffff};

    @Override
    public List<List<RowCell>> handleData() {
        List<List<String>>[] handledData = getHandledData();
        return getResultData(handledData);
    }

    @Override
    public Object[] getTextArr(int type, int rowIndex, int position, String text) {
        return text.split(",");
    }

    @Override
    public int getRowBColor(int type, int rowIndex) {
        int bgColor;
        if (type == 0 || type == 1) {
            bgColor = bgRowColors[1];
        } else {
            bgColor = bgRowColors[rowIndex % 2];
        }
        return bgColor;
    }

    @Override
    public int getTextColor(int type, int rowIndex, int position, String text) {
        return 0xffff0000;
    }

    @Override
    public int getBottomLineStrokeSize(int type, int rowIndex, int totalRow) {
        return rowIndex == totalRow - 1 ? Math.round(dp_1) : 0;
    }

    @Override
    public int getHorizontalSpacing(int type, int rowIndex, int position, String text) {
        return Math.round(dp_1 * 3);
    }

    @Override
    public RowCell.StrokeInfo getStrokeInfo(int type, int rowIndex, int position) {
        if (type == 3 && position == 1) {
            RowCell.StrokeInfo strokeInfo = new RowCell.StrokeInfo();
            strokeInfo.isStroke = true;
            strokeInfo.strokeColor = 0xFFDBDBDF;
            strokeInfo.strokeWidth = dp_1;
            return strokeInfo;
        }
        return null;
    }

    @Override
    public int getTextRectSize(int type, int rowIndex, int position, String text) {
        return Math.round(dp_1 * 20);
    }

    @Override
    public int getTextRectBgRadius(int type, int rowIndex, int position, String text) {
        return 0;
    }

    @Override
    public int getCellWidth(int type, int rowIndex, int position) {
        if (type == 0 || type == 2) {
            return width_1;
        } else {
            return position == 0 ? width_2 : width_3;
        }
    }

}
