package com.common.widget.chart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.comm.L;


/**
 * Author: L
 * 2018/12/26
 * Description:
 */
public class HRecyclerView extends RecyclerView {

    private HLayoutManager layoutManager;
    private float min_scroll_unit;

    public HRecyclerView(Context context) {
        this(context, null);
    }

    public HRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        min_scroll_unit = L.dp_1;
        // LogUtil.d("=========min_scroll_unit:" + min_scroll_unit);
    }

    private float startX;
    private float startY;
    private int orientation = 0;
    private static final int orientation_vertical = 1;
    private static final int orientation_horizontal = 2;
    private static final int max_compute_times = 3;
    private int compute_times = 0;
    private float xScrollSum;
    private float yScrollSum;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                orientation = 0;
                xScrollSum = 0;
                yScrollSum = 0;
                compute_times = 0;
                layoutManager.setMyOrientation(HLayoutManager.ALL);
                // LogUtil.d("=======================DOWN============================:");
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getRawX();
                float currentY = ev.getRawY();
                float dx = currentX - startX;
                float dy = currentY - startY;

                if (compute_times < max_compute_times || (Math.abs(xScrollSum) < min_scroll_unit &&
                        Math.abs(yScrollSum) < min_scroll_unit)) {//????????????????????????????????? ??????????????????????????????
                    xScrollSum += dx;
                    yScrollSum += dy;
                    compute_times++;
                }
                startX = currentX;
                startY = currentY;
                // LogUtil.d("===1==??????===:" + " xScrollSum:" + xScrollSum + " yScrollSum:" + yScrollSum + " dx:" + dx + "  dy:" + dy);
                if (Math.abs(xScrollSum) > min_scroll_unit && Math.abs(xScrollSum) > Math.abs(yScrollSum)) {
                    if (orientation == 0) orientation = orientation_horizontal;
                    if (orientation == orientation_horizontal) {
                        layoutManager.setMyOrientation(LinearLayoutManager.HORIZONTAL);
                    }

                } else if (Math.abs(yScrollSum) > min_scroll_unit && Math.abs(yScrollSum) > Math.abs(xScrollSum)) {
                    if (orientation == 0) orientation = orientation_vertical;
                    if (orientation == orientation_vertical) {
                        layoutManager.setMyOrientation(LinearLayoutManager.VERTICAL);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                postDelayed(() -> layoutManager.setMyOrientation(HLayoutManager.ALL),100);//??????????????????????????? ??????????????????????????????????????????????????????
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        layoutManager = (HLayoutManager) getLayoutManager();
    }
}