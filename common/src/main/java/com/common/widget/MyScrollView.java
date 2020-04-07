package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isScrollEnable() {
        return scrollEnable;
    }

    private boolean scrollEnable = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollEnable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }


    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }
}
