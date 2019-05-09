package com.common.widget.comm;

import android.content.Context;
import android.view.MotionEvent;

import com.common.R;
import com.common.listener.OnClickListener2;
import com.common.listener.OnOrientationChangedListener;

public class TouchEventHelper {
    private float startX;
    private float startY;
    private int orientation = 0;
    private float xScrollSum;
    private float yScrollSum;
    private int compute_times = 0;

    public static final int orientation_vertical = 1;
    public static final int orientation_horizontal = 2;
    private static final int max_compute_times = 3;
    private float min_scroll_unit;
    private long down_time;
    private float down_x;
    private float down_y;

    public TouchEventHelper(Context context) {
        min_scroll_unit = context.getResources().getDimension(R.dimen.dp_1);
    }

    public void getTouchOrientation(MotionEvent ev, OnOrientationChangedListener listener) {
        float rawX = ev.getRawX();
        float rawY = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_time = System.currentTimeMillis();
                startX = rawX;
                startY = rawY;
                down_x = startX;
                down_y = startY;
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
                        listener.onOrientationChanged(orientation_horizontal);
                    }
                } else if (Math.abs(yScrollSum) > min_scroll_unit && Math.abs(yScrollSum) > Math.abs(xScrollSum)) {
                    if (orientation == 0) orientation = orientation_vertical;
                    if (orientation == orientation_vertical) {
                        listener.onOrientationChanged(orientation_vertical);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                long clickTime = System.currentTimeMillis() - down_time;
                boolean clickCondition_1 = clickTime < 200 && Math.abs(rawX - down_x) < min_scroll_unit * 5 && Math.abs(rawY - down_y) < min_scroll_unit * 5;
                boolean clickCondition_2 = Math.abs(rawX - down_x) == 0 && Math.abs(rawY - down_y) == 0;
                if (clickCondition_1 || clickCondition_2) {
                    if (onClickListener != null) onClickListener.onClick(ev);
                }
                break;
        }
    }

    private OnClickListener2 onClickListener;

    public void setOnClickListener(OnClickListener2 onClickListener) {
        this.onClickListener = onClickListener;
    }

}
