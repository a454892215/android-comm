package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.common.widget.comm.TouchEventHelper;

/**
 * Author:
 * 2020/11/15
 * Description:
 */
public class MyViewPager extends FrameLayout {
    private final TouchEventHelper touchEventHelper;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchEventHelper = new TouchEventHelper(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        touchEventHelper.onDispatchTouchEvent(ev, orientation -> {
            if(orientation == TouchEventHelper.ori_v){
            }
        });
        return  super.dispatchTouchEvent(ev);
    }

}

