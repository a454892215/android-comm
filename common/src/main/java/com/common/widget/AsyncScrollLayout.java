package com.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.common.utils.LogUtil;

public class AsyncScrollLayout extends LinearLayout {
    public AsyncScrollLayout(Context context) {
        this(context, null);
    }

    public AsyncScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AsyncScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            try {
                child.dispatchTouchEvent(event);
            } catch (Exception e) {
                LogUtil.e("======发生异常：" + e.getMessage());
                e.printStackTrace();
            }
        }
        return true;
    }
}
