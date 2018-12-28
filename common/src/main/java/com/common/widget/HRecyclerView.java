package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.common.R;
import com.common.adapter.common.HLayoutManager;
import com.common.utils.LogUtil;

/**
 * Author: Pan
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
        min_scroll_unit = context.getResources().getDimension(R.dimen.dp_1);

        LogUtil.debug("=========min_scroll_unit:" + min_scroll_unit);
        addOnItemTouchListener(new OnScroll());
    }

    float startX;
    float startY;
    int orientation = 0;
    int orientation_vertical = 1;
    int orientation_horizontal = 2;

    private class OnScroll implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = ev.getRawX();
                    startY = ev.getRawY();
                    orientation = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float currentX = ev.getRawX();
                    float currentY = ev.getRawY();
                    float dx = currentX - startX;
                    float dy = currentY - startY;
                    startX = currentX;
                    startY = currentY;
                    // LogUtil.d("=========================滑动===:" + " dx:" + dx + " dy:" + dy);
                    if (Math.abs(dx) < min_scroll_unit && Math.abs(dy) < min_scroll_unit) {
                        return false;
                    }
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (orientation == 0) orientation = orientation_horizontal;
                        if (orientation == orientation_horizontal) {
                            layoutManager.setMyOrientation(LinearLayoutManager.HORIZONTAL);
                        }
                    } else {
                        if (orientation == 0) orientation = orientation_vertical;
                        if (orientation == orientation_vertical) {
                            layoutManager.setMyOrientation(LinearLayoutManager.VERTICAL);
                        }
                        //   LogUtil.d("======================垂直======滑动===:" + " dx:" + dx + " dy:" + dy);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        layoutManager = (HLayoutManager) getLayoutManager();
    }
}