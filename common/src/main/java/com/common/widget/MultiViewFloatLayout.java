package com.common.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;
import com.common.utils.DensityUtils;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;
import com.common.widget.comm.TouchEventHelper;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class MultiViewFloatLayout extends FrameLayout {

    //  OnSelectChangedListener listener;
    private float dp_1;
    private float width;
    private float height;
    private ViewDragHelper viewDragHelper;
    private TouchEventHelper touchEventHelper;
    private long removeViewTime;

    public MultiViewFloatLayout(Context context) {
        this(context, null);
    }

    public MultiViewFloatLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    private int orientation = 0;

    public MultiViewFloatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp_1 = getResources().getDimension(R.dimen.dp_1);
        width = DensityUtils.getWidth(context);
        height = DensityUtils.getHeight(context);
        touchEventHelper = new TouchEventHelper(context);
        viewDragHelper = ViewDragHelper.create(this, new CallBack());
    }

    private void verticalScrollAllChildView(float dy) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            float minTop = (int) view.getTag(R.id.key_tag_position);
            float newDy = dy * ((i + 1f) / childCount);
            float newTop = view.getTop() + newDy;
            float top_scrolling = MathUtil.clamp(newTop, minTop, minTop + dp_1 * 80 * (i + 0.2f));
            view.setTop(Math.round(top_scrolling));
            if (i == 0) {
                LogUtil.d("=================newDy:" + newDy + "   top_scrolling:" + top_scrolling);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        touchEventHelper.getTouchOrientation(event, ori -> orientation = ori);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
            invalidate();
        }
        if (System.currentTimeMillis() - removeViewTime < 1000) return;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view == null) return;
            int left = view.getLeft();
            if (left == -view.getWidth() || left == getWidth()) {
                removeView(view);
                removeViewTime = System.currentTimeMillis();
                LogUtil.d("=============left:" + left + "   view.getWidth" + view.getWidth() + "  i:" + i);
            }
        }
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        int childCount = getChildCount();
        float scale = 0.6f;
        float initMarginTop = dp_1 * 30;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            lp.width = Math.round(width * scale);
            lp.height = Math.round(height * scale);
            lp.topMargin = Math.round(initMarginTop * i);
            view.setTag(R.id.key_tag_position, lp.topMargin + getPaddingTop());
            view.setLayoutParams(lp);
            scale = scale * 1.1f;
        }
    }
/*
    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int position);
    }*/


    private class CallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (orientation == TouchEventHelper.orientation_horizontal) return left;
            return child.getLeft();
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (orientation == TouchEventHelper.orientation_vertical) {
                postDelayed(() -> verticalScrollAllChildView(dy), 30);
            }
            return child.getTop();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            //  super.onViewReleased(releasedChild, xvel, yvel);
            int currentLeft = releasedChild.getLeft();
            int left = Math.round((getWidth() - releasedChild.getWidth()) / 2f);
            if (currentLeft < -releasedChild.getWidth() / 2) {
                left = -releasedChild.getWidth();
            }
            if (currentLeft > getWidth() - releasedChild.getWidth() / 2) {
                left = getWidth();
            }
            viewDragHelper.settleCapturedViewAt(left, releasedChild.getTop());
            invalidate();
            if (orientation == TouchEventHelper.orientation_vertical) {
                ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
                animator.setDuration(200);
                animator.addUpdateListener(animation -> {
                    float value = (float) animation.getAnimatedValue();
                    verticalScrollAllChildView(Math.round(value * (yvel / 1000) * 9));
                });
                animator.start();
            }
        }
    }

}
