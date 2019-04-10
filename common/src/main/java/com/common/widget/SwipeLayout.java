package com.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.common.R;

public class SwipeLayout extends HorizontalScrollView {


    private static int canScrollMaxValue;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (canScrollMaxValue == 0) {
                    canScrollMaxValue = getChildAt(0).getWidth() - getWidth();
                }
                break;
            case MotionEvent.ACTION_UP:
                postDelayed(() -> {
                    boolean isScrollBack = false;
                    if (getScrollX() < canScrollMaxValue / 2) {
                        smoothScrollTo(0, 0);
                        isScrollBack = true;
                        recordOPenPosition(false);
                    } else if (getScrollX() < canScrollMaxValue) {
                        smoothScrollTo(canScrollMaxValue, 0);
                    }
                    if (!isScrollBack) {
                        recordOPenPosition(true);
                        closeOtherSwipe();
                    }
                }, 100);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void closeOtherSwipe() {
        Object parent_obj = getParent();
        if (parent_obj instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) parent_obj;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                if (child instanceof SwipeLayout && child != this && child.getScrollX() == canScrollMaxValue) {
                    ((SwipeLayout) child).smoothScrollTo(0, 0);
                }
            }
        }
    }

    //RV兼容处理
    private void recordOPenPosition(boolean isOpen) {
        if (getParent() instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) getParent();
            if (isOpen) {
                int position = rv.getChildAdapterPosition(this);
                rv.setTag(R.id.key_tag_open_position, position);
            } else {
                rv.setTag(R.id.key_tag_open_position, -2);
            }
        }
    }

    //RV兼容处理
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (getParent() instanceof RecyclerView) {
            if (visibility == View.GONE) {
                setScrollX(0);
            } else if (visibility == View.VISIBLE) {
                RecyclerView rv = (RecyclerView) getParent();
                int position = rv.getChildAdapterPosition(this);
                Object tag = rv.getTag(R.id.key_tag_open_position);
                int openPosition = tag == null ? -2 : (int) tag;
                if (openPosition == position) {
                    setScrollX(canScrollMaxValue);
                }
            }
        }
    }

    public static void clearOPenPosition(RecyclerView rv) {
        int childCount = rv.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = rv.getChildAt(i);
            if (child instanceof SwipeLayout) {
                child.scrollTo(0, 0);
            }
        }
        rv.setTag(R.id.key_tag_open_position, -2);
    }

}
