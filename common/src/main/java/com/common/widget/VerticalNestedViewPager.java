package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.common.utils.LogUtil;
import com.common.widget.comm.TouchEventHelper;

/**
 * Author:
 * 2020/11/15
 * Description:
 */
public class VerticalNestedViewPager extends ViewPager {
    private final TouchEventHelper touchEventHelper;

    public VerticalNestedViewPager(Context context) {
        this(context, null);
    }

    public VerticalNestedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchEventHelper = new TouchEventHelper(context);
       // setNestedScrollingEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        touchEventHelper.onDispatchTouchEvent(ev, orientation -> {
            if(orientation == TouchEventHelper.ori_v){
                CoordinatorLayout parent = (CoordinatorLayout) VerticalNestedViewPager.this.getParent();
                parent.dispatchNestedPreScroll(0, 3, new int[2], new int[2]);
            }
        });
        return  super.dispatchTouchEvent(ev);
    }

}

