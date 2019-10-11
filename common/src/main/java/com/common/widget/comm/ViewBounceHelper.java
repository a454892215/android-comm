package com.common.widget.comm;

import android.view.MotionEvent;
import android.view.View;

import com.common.R;
import com.common.utils.LogUtil;

public class ViewBounceHelper {
    private float startX;
    private float startY;
    private float xScrollSum;
    private float yScrollSum;
    private int compute_times = 0;
    private static final int max_compute_times = 3;

    private int orientation = 0;
    private static final int orientation_vertical = 1;
    private static final int orientation_horizontal = 2;
    private float min_scroll_unit;
    private View view;

    public ViewBounceHelper(View view) {
        this.view = view;
        min_scroll_unit = view.getResources().getDimension(R.dimen.dp_1);
        view.setOnTouchListener((view1, motionEvent) -> {
            start(motionEvent);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view1.performClick();
            }
            return true;
        });
    }

    public void start(MotionEvent ev) {
        float rawX = ev.getRawX();
        float rawY = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = rawX;
                startY = rawY;
                orientation = 0;
                xScrollSum = 0;
                yScrollSum = 0;
                compute_times = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = rawX - startX;
                float dy = rawY - startY;
                if (compute_times < max_compute_times || (Math.abs(xScrollSum) < min_scroll_unit &&
                        Math.abs(yScrollSum) < min_scroll_unit)) {//第一个条件限制最大次数 ，避免首几次数据有误
                    xScrollSum += dx;
                    yScrollSum += dy;
                    compute_times++;
                }
                startX = rawX;
                startY = rawY;
                if (Math.abs(xScrollSum) > min_scroll_unit && Math.abs(xScrollSum) > Math.abs(yScrollSum)) {
                    if (orientation == 0) orientation = orientation_horizontal;
                    if (orientation == orientation_horizontal) {
                        onScroll(dx, 0);
                    }
                } else if (Math.abs(yScrollSum) > min_scroll_unit && Math.abs(yScrollSum) > Math.abs(xScrollSum)) {
                    if (orientation == 0) orientation = orientation_vertical;
                    if (orientation == orientation_vertical) {
                        onScroll(0, dy);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void onScroll(float dx, float dy) {

        if (dx != 0) { //水平
            if (dx > 0){
                int paddingStart = view.getPaddingStart() + Math.round(dx);
                view.setPadding(paddingStart,0,0,0);
                LogUtil.d("========1======dx:" + dx + "  dy:" + dy + " paddingStart:"+view.getPaddingStart() +"  paddingStart:"+paddingStart);
            }else{
                int paddingEnd = view.getPaddingStart() + Math.round(dx);
                view.setPadding(0,0,paddingEnd,0);
            }

        } else { //垂直

        }
    }


}
