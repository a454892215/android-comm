package com.common.widget.table;

import android.graphics.Color;
import android.text.SpannableStringBuilder;

import java.util.List;

/**
 * Author:  Liu Pan
 * CreateDate: 2018/12/13
 * Description: No
 */

public class RowCell {
    public List<TableCellEntity> list;
    public int rowBgColor = Color.WHITE;

    //  public boolean isFirstLine = false;
    // public boolean isLastLine = false;
    public static class TableCellEntity {
        public TableCellEntity(String name, int cellWidth, int cellHeight, int textColor) {
            this.name = name;
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
            this.textColor = textColor;
        }

        public String name;
        public int cellWidth;
        public int cellHeight;
        public int textColor;

        public boolean isDrawingBg = false;
        public int drawingBgColor;
        public boolean isDrawingCircleBg = true; //是否绘制圆形背景 否则矩形
        public float bgRectWidth = 0;
        public float bgRectHeight = 0;
        public int paddingTop = 0;
        public int paddingBottom = 0;
        public boolean isDrawTopLine = false;
        public boolean isDrawBottomLine = false;
        public boolean isDrawRightLine = false;
        public boolean isDrawLeftLine = false;
        // public boolean drawBgAnimationEnable = false;
        public int roundBgRadius;
        public int round_rect_radius;
        public int lineColor;
        public int lineWidth;
        public boolean isShowTv2 = false; //是否显示tv2
        public String tv2Name;
        public int tv2Size;
        public float tv2TextSize;
        public int tv2TextColor;
        public int tv2BgColor;

        public float x; //以本身长度为单位 cell右边界到 自定义左边界的距离
        public float y; //以本身长度为单位 cell顶底界到 自定义顶边界的距离

        public SpannableStringBuilder span;
    }
}
