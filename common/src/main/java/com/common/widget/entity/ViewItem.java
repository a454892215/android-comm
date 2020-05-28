package com.common.widget.entity;

import android.view.View;

/**
 * Author: Pan
 * 2020/5/28
 * Description:
 */
public class ViewItem {
    public float top;
    public float bottom;
    public float start;
    public float end;
    public Object data;
    public int index;
    public float offset;


    /**
     * 在View的左边超屏 不可见
     */
    public boolean isOnViewLeft(View view) {
        return end < 0;
    }

    /**
     * 在View的右边超屏 不可见
     */
    public boolean isOnViewRight(View view) {
        return start > view.getMeasuredWidth();
    }
}
