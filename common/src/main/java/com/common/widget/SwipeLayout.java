package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.common.R;

public class SwipeLayout extends HorizontalScrollView {
    private int canScrollMaxValue;

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
                canScrollMaxValue = getChildAt(0).getWidth() - getWidth();
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
                    //隐藏上一个
                    if (!isScrollBack) {
                        View parent = (View) getParent();
                        Object obj = parent.getTag(R.id.key_tag_swipe_open_view);
                        if (obj != this && obj instanceof HorizontalScrollView) {
                            HorizontalScrollView last_hsv = (HorizontalScrollView) obj;
                            last_hsv.smoothScrollTo(0, 0);
                        }
                        parent.setTag(R.id.key_tag_swipe_open_view, this);
                    }
                }, 100);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}
