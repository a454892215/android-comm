package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class SwipeLayout extends HorizontalScrollView {


    private static int canScrollMaxValue;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (canScrollMaxValue == 0) {
                    canScrollMaxValue = getChildAt(0).getWidth() - getWidth();
                }
                break;
            case MotionEvent.ACTION_UP:
                postDelayed(() -> {
                    boolean isScrollBack = false;
                    if (getScrollX() < canScrollMaxValue / 2) {
                        smoothScrollTo(0, 0);
                        isScrollBack = true;
                    } else if (getScrollX() < canScrollMaxValue) {
                        smoothScrollTo(canScrollMaxValue, 0);
                    }
                    //隐藏其他展开的View
                    if (!isScrollBack) {
                        closeOtherSwipeView();
                    }
                }, 100);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void closeOtherSwipeView() {
        Object parent_obj = getParent();
        if (parent_obj instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) parent_obj;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                if (child instanceof SwipeLayout && child != this && child.getScrollX() == canScrollMaxValue) {
                    ((SwipeLayout) child).smoothScrollTo(0, 0);
                }
            }
        }
    }
}
