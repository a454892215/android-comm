package com.common.widget.chart;

import android.graphics.Color;

import com.common.widget.view_func.BgTextShape;

import java.util.List;

/**
 * Author:  L
 * Description: No
 */

public class RowCell {
    public List<TableCellEntity> list;
    public int rowBgColor = Color.WHITE;

    public static class TableCellEntity {

        TableCellEntity(int cellWidth, int cellHeight) {
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
        }

        public int horizontalSpacing;
        public Object[] textArr;
        public int[] bgColorArr;
        public int cellWidth;
        public int cellHeight;
        public int textColor;
        public int[] textColors;
        public int lineColor;

        public int rightLineStrokeSize = 0;
        public int topLineStrokeSize = 0;
        public int bottomLineStrokeSize = 0;
        public int leftLineStrokeSize = 0;

        public int rectHeight;
        public int rectSize;
        public int rectBgRadius;

        public BgTextShape bgTextShape;

        public StrokeInfo strokeInfo;
    }

    public static class StrokeInfo {
        public boolean isStroke;
        public int strokeColor;
        public float strokeWidth;
    }

}
