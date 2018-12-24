package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步滚动
 */
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < asyncScrollGroupList.size(); i++) {
                    AsyncScrollGroup asyncScrollGroup = asyncScrollGroupList.get(i);
                    for (RecyclerView rv : asyncScrollGroup.syncedRVArr) {
                        if (isTouchPointInView(rv, ev.getRawX(), ev.getRawY()))
                            asyncScrollGroup.asyncScrollRecyclerView(rv);
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    List<AsyncScrollGroup> asyncScrollGroupList = new ArrayList<>();

    public void addRecyclerViewGroup(RecyclerView... syncedRV) {
        AsyncScrollGroup asyncScrollGroup = new AsyncScrollGroup();
        asyncScrollGroup.setSyncedRVArr(syncedRV);
        asyncScrollGroupList.add(asyncScrollGroup);
    }

    private boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }


    private static class AsyncScrollGroup {
        private RecyclerView lastScrollView; //记录上次主动滚动的RecyclerView
        private RecyclerView[] syncedRVArr;

        private void setSyncedRVArr(RecyclerView[] syncedRVArr) {
            this.syncedRVArr = syncedRVArr;
        }

        private void asyncScrollRecyclerView(RecyclerView handleTouchRV) {
            if (handleTouchRV == lastScrollView) {
                return;
            } else {
                if (lastScrollView != null) {
                    int scrollState = lastScrollView.getScrollState();
                    if (scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                        lastScrollView.stopScroll();
                    }
                }
            }
            for (RecyclerView rv : syncedRVArr) {
                rv.removeOnScrollListener(onScrollListener);
            }
            handleTouchRV.addOnScrollListener(onScrollListener);
            lastScrollView = handleTouchRV;
        }

        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (RecyclerView rv : syncedRVArr) {
                    if (recyclerView != rv) rv.scrollBy(dx, dy);
                }
            }
        };
    }
}
