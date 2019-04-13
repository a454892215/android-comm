package com.common.widget.table;

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

    //  public boolean isFirstLine = false;
    // public boolean isLastLine = false;
    public static class TableCellEntity {
        TableCellEntity(int cellWidth, int cellHeight) {
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
        }

        public int cellWidth;
        public int cellHeight;
        public int textColor;
        boolean isDrawingBg = false;
        int drawingBgColor;
        boolean isDrawTopLine = false;
        boolean isDrawBottomLine = false;
        boolean isDrawRightLine = false;
        boolean isDrawLeftLine = false;
        // public boolean drawBgAnimationEnable = false;
        int roundBgRadius;
        public int round_rect_radius;
        public int lineColor;
        int lineStrokeSize;

        public float x; //以本身长度为单位 cell右边界到 自定义左边界的距离
        public float y; //以本身长度为单位 cell顶底界到 自定义顶边界的距离

        public SpannableStringBuilder span;
    }
}
