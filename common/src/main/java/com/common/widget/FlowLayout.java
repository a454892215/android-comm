package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;

public class FlowLayout extends FrameLayout {

    int verticalSpacing;//行间距
    int horizontalSpacing; //水平相邻间距

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        verticalSpacing = Math.round(10 * dp_1);
        horizontalSpacing = Math.round(10 * dp_1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        int item_height = 0;
        if (childCount > 0) {
            item_height = getChildAt(0).getHeight();
        }
        int item_left = getPaddingStart();
        int item_top = getPaddingTop();
        int item_right;
        int item_bottom;
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);
            //判断是否应该换行
            if (item_left + itemView.getMeasuredWidth() + getPaddingEnd() > getMeasuredWidth()) {
                item_left = getPaddingStart();
                item_top = item_top + item_height + verticalSpacing;
            }
            item_right = item_left + itemView.getMeasuredWidth();
            item_bottom = item_top + itemView.getMeasuredHeight();
            itemView.layout(item_left, item_top, item_right, item_bottom);
            item_left = item_right + horizontalSpacing;
        }
    }
}
