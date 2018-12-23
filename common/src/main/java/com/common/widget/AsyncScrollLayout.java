package com.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

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
        if (handleTouchRV != null) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void asyncScrollRecyclerView() {
        handleTouchRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (RecyclerView rv : syncedRVArr) {
                    rv.scrollBy(dx, dy);
                }
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if (handleTouchRV != null) {
            handleTouchRV.dispatchTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    private RecyclerView handleTouchRV;
    private RecyclerView[] syncedRVArr;

    public void setRecyclerView(RecyclerView handleTouchRV, RecyclerView... syncedRV) {
        this.handleTouchRV = handleTouchRV;
        this.syncedRVArr = syncedRV;
        asyncScrollRecyclerView();
    }
}
