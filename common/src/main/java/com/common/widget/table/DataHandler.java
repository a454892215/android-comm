package com.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;


import com.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:  Pan
 * CreateDate: 2019/1/5 9:46
 * Description: 走势图总处理器
 */

public class DataHandler {
    int chart_red;
    int chart_blue;
    int cellWidth_left_date;
    int cellHeight;
    int text_color_1 = Color.parseColor("#606060");
    Context context;
    static final String key_resultInfo = "resultInfo";
    private static final String text_issue = "期号";
    private static final String key_issue = "issue";
    private int line_stroke_width;
    public static int roundBgRadius;
    private final int lineColor;
    private final int bgColor_1;
    private final int bgColor_2;

    boolean isRemovePrefix_0 = false;
    List<String> trendPointList = new ArrayList<>();
    int[] right_widths;

    DataHandler(Context context) {
        this.context = context;
        cellWidth_left_date = Math.round(context.getResources().getDimension(R.dimen.dp_50));
        cellHeight = Math.round(context.getResources().getDimension(R.dimen.dp_30));
        line_stroke_width = Math.round(context.getResources().getDimension(R.dimen.dp_1));
        if (roundBgRadius == 0) {
            roundBgRadius = Math.round(context.getResources().getDimension(R.dimen.dp_11));
        }
        lineColor = Color.parseColor("#cccccc");
        bgColor_1 = Color.parseColor("#F8F8F8");
        bgColor_2 = Color.WHITE;

        chart_red = Color.RED;
        chart_blue = Color.BLUE;
    }


    public List<List<RowCell>> handleData(List<Map<String, String>> list) {
        return null;
    }

    @NonNull
    List<List<RowCell>> getResultData(List<Map<String, String>> list, String[] keys_1, String[] names_1, int[] rightWidths) {
        trendPointList.clear();
        ArrayList<RowCell> bodyLeftRowCellList = new ArrayList<>();
        ArrayList<RowCell> bodyRightRowCellList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Map<String, String> map = list.get(i);
            String value_issue = map.get(key_issue);
            bodyLeftRowCellList.add(getBodyLeftRowCell(value_issue.substring(value_issue.length() - 3), i, size));//添加每行期号
            bodyRightRowCellList.add(getBodyRightRowCell(getBodyRowName(keys_1, map), rightWidths, i, size));//添加每行body
        }

        ArrayList<RowCell> headerLeftRowCellList = new ArrayList<>();
        headerLeftRowCellList.add(getHeaderLeftRowCell());

        ArrayList<RowCell> headerRightRowCellList = new ArrayList<>();
        headerRightRowCellList.add(getHeaderRightRowCell(names_1, rightWidths));

        List<List<RowCell>> resultList = new ArrayList<>();
        resultList.add(headerLeftRowCellList); //header左边
        resultList.add(headerRightRowCellList);//header右边
        resultList.add(bodyLeftRowCellList); //body左边
        resultList.add(bodyRightRowCellList); //body右边
        return resultList;
    }

    @NonNull
    protected String[] getBodyRowName(String[] keys_1, Map<String, String> map) {
        String[] rightNames = new String[keys_1.length];
        for (int j = 0; j < keys_1.length; j++) {
            String key = keys_1[j];
            String value = map.get(key);
            value = value == null ? key : value;
            if (isRemovePrefix_0 && key_resultInfo.equals(key)) {
                value = value.replaceAll("0([0-9])", "$1");
            }
            rightNames[j] = value;
        }
        return rightNames;
    }

    private RowCell getHeaderLeftRowCell() {
        RowCell rowCell = new RowCell();
        rowCell.list = new ArrayList<>();
        rowCell.rowBgColor = bgColor_2;
        RowCell.TableCellEntity entity = new RowCell.TableCellEntity(DataHandler.text_issue, cellWidth_left_date, cellHeight, text_color_1);
        entity.isDrawRightLine = true;
        entity.isDrawTopLine = true;
        entity.lineWidth = line_stroke_width;
        entity.lineColor = lineColor;
        rowCell.list.add(entity);
        return rowCell;
    }

    private RowCell getHeaderRightRowCell(String[] titles, int[] width) {
        right_widths = width;
        RowCell rowCell = new RowCell();
        rowCell.list = new ArrayList<>();
        rowCell.rowBgColor = bgColor_2;
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            RowCell.TableCellEntity entity = new RowCell.TableCellEntity(title, width[i], cellHeight, text_color_1);
            entity.isDrawLeftLine = i != 0;
            entity.isDrawTopLine = true;
            entity.lineWidth = line_stroke_width;
            entity.lineColor = lineColor;
            rowCell.list.add(entity);
        }
        return rowCell;
    }

    private RowCell getBodyLeftRowCell(String name, int rowIndex, int totalRow) {
        RowCell rowCell = new RowCell();
        rowCell.rowBgColor = rowIndex % 2 == 0 ? bgColor_1 : bgColor_2;
        rowCell.list = new ArrayList<>();
        RowCell.TableCellEntity entity = new RowCell.TableCellEntity(name, cellWidth_left_date, cellHeight, text_color_1);
        entity.isDrawRightLine = true;
        entity.isDrawBottomLine = rowIndex == totalRow - 1;
        entity.lineWidth = line_stroke_width;
        entity.lineColor = lineColor;
        rowCell.list.add(entity);
        return rowCell;
    }

    private RowCell getBodyRightRowCell(String[] titles, int[] width, int rowIndex, int totalRow) {
        RowCell rowCell = new RowCell();
        rowCell.list = new ArrayList<>();
        rowCell.rowBgColor = rowIndex % 2 == 0 ? bgColor_1 : bgColor_2;
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            RowCell.TableCellEntity entity = new RowCell.TableCellEntity(title, width[i], cellHeight, text_color_1);
            entity.textColor = getTextColor(i);
            entity.isDrawingBg = isDrawingBg(i);
            entity.drawingBgColor = getDrawingBgColor(i, title);
            entity.isDrawingCircleBg = isDrawingCircleBg(i);
            entity.isDrawLeftLine = i != 0;
            entity.isDrawBottomLine = rowIndex == totalRow - 1;
            entity.roundBgRadius = roundBgRadius;
            entity.lineColor = lineColor;
            entity.lineWidth = line_stroke_width;
            entity.span = getSpan(i, title);
            rowCell.list.add(entity);
        }
        return rowCell;
    }

    int index_tab_level_2;

    public DataHandler set2LevelTabIndex(int index) {
        index_tab_level_2 = index;
        return this;
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

    //======================以下给子类重写 决定table body部分不同样式
    protected int getTextColor(int position) {
        return text_color_1;
    }

    protected boolean isDrawingBg(int position) {
        return false;
    }

    protected int getDrawingBgColor(int position, String text) {
        return Color.RED;
    }

    protected SpannableStringBuilder getSpan(int position, String text) {
        return null;
    }

    protected boolean isDrawingCircleBg(int position) {
        return false;
    }
}
