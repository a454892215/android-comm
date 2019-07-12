package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;

public class FlowTableLayout extends FrameLayout {

    int verticalSpacing;//行间距
    int columns = 4; //列数
    int item_height;

    public FlowTableLayout(Context context) {
        this(context, null);
    }

    public FlowTableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        verticalSpacing = Math.round(10 * dp_1);
        item_height = Math.round(90 * dp_1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int item_height = 0;
        int childCount = getChildCount();
        if (childCount > 0) {
            item_height = getChildAt(0).getMeasuredHeight();
        }
        int rows = (childCount % columns) == 0 ? childCount / columns : childCount / columns + 1;//行数
        int height = item_height * rows + verticalSpacing * (rows - 1) + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        int item_width = getMeasuredWidth() / columns;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int position_horizontal = i % columns;
            int item_left = position_horizontal * item_width;
            int row_index = i % columns;//列号
          //  int item_top =
        }

    }
}
