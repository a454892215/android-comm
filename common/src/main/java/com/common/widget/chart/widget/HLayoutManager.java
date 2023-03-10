package com.common.widget.chart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:  L
 * Description: No
 */

public class HLayoutManager extends LinearLayoutManager {

    private int canScrollWidth;

    HLayoutManager(Context context) {
        super(context);
    }
    @SuppressWarnings("unused")
    public HLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
    @SuppressWarnings("unused")
    public HLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (canScrollWidth == 0) {
            int horizontalContentWidth = getWidth() - getPaddingLeft() + getPaddingRight();
            View view = recycler.getViewForPosition(0);
            measureChildWithMargins(view, 0, 0);
            int itemWidth = getDecoratedMeasuredWidth(view);
            canScrollWidth = itemWidth - horizontalContentWidth;
            canScrollWidth = canScrollWidth < 0 ? 0 : canScrollWidth;
            // LogUtil.d("canScrollWidth:" + canScrollWidth + " itemWidth:" + itemWidth + "  horizontalContentWidth:" + horizontalContentWidth);
        }
    }

    void setMyOrientation(int orientation) {
        this.orientation = orientation;
    }

    private int orientation = HLayoutManager.ALL;

    static final int ALL = 2;

    @Override
    public boolean canScrollHorizontally() {
        return orientation == HORIZONTAL || orientation == ALL;
    }

    @Override
    public boolean canScrollVertically() {
        return orientation == VERTICAL || orientation == ALL;
    }

    private int dx_scrolled;

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx_scrolled + dx < 0) { //???????????????
            dx = 0 - dx_scrolled;
        } else if (dx_scrolled + dx > canScrollWidth) { //???????????????
            dx = canScrollWidth - dx_scrolled;
        }
        offsetChildrenHorizontal(-dx);
        dx_scrolled += dx;
        return dx;
    }

    @Override
    public void layoutDecoratedWithMargins(View child, int left, int top, int right, int bottom) {
        left += -dx_scrolled;
        super.layoutDecoratedWithMargins(child, left, top, right, bottom);
    }
}
