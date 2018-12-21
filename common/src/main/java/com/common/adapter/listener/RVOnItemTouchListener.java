package com.common.adapter.listener;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Author:  Pan
 * CreateDate: 2018/12/20 18:37
 * Description: No
 */

public class RVOnItemTouchListener implements RecyclerView.OnItemTouchListener {
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
