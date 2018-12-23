package com.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.common.utils.LogUtil;

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
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        if (childCount > 1) {
            View view = getChildAt(0);
            asyncScrollRecyclerView(childCount, view);

        }
    }

    private void asyncScrollRecyclerView(int childCount, View view) {
        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LogUtil.d("===========onAttachedToWindow========:"+recyclerView.getScrollY());
                   for (int i = 1; i < childCount; i++) {
                        View view = getChildAt(i);
                       if(view instanceof RecyclerView){
                           RecyclerView rv = (RecyclerView) view;
                           rv.scrollBy(dx,dy);
                       }
                    }
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int count = getChildCount();
        if(count > 0){ //直接把事件交给第一个view处理
            View child = getChildAt(0);
            child.dispatchTouchEvent(event);
        }
        return true;
    }
}
