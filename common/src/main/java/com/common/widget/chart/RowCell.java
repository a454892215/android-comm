package com.common.widget.chart;

import android.graphics.Color;
import android.text.SpannableStringBuilder;

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

        public int cellWidth;
        public int cellHeight;
        public int textColor;
        public int lineColor;
        public int lineStrokeSize = 2;
        public int topLineStrokeSize = 0;
        public int leftLineStrokeSize = 0;
        public float x; //以本身长度为单位 cell右边界到 自定义左边界的距离
        public float y; //以本身长度为单位 cell顶底界到 自定义顶边界的距离

        public SpannableStringBuilder span;
    }
}
