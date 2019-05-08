package com.common.widget.float_window;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;
import com.common.utils.DensityUtils;
import com.common.utils.MathUtil;
import com.common.utils.ViewUtil;
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
    private TouchEventHelper touchEventHelper;
    private GestureDetector gestureDetector;
    private SimpleGestureListener simpleGestureListener;

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
        unitMarginTop = dp_1 * 30;
        width = DensityUtils.getWidth(context);
        height = DensityUtils.getHeight(context);
        minWidth = minWidthScale * width;
        maxWidth = maxWidthScale * width;
        touchEventHelper = new TouchEventHelper(context);
        simpleGestureListener = new SimpleGestureListener();
        gestureDetector = new GestureDetector(context, simpleGestureListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        touchEventHelper.getTouchOrientation(event, ori -> orientation = ori);
        return super.dispatchTouchEvent(event);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            simpleGestureListener.onUp();
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        onChildViewCountChanged();
    }

    private float unitMarginTop;

    private static final float minWidthScale = 0.6f;
    private static final float maxWidthScale = 0.8f;
    private float minWidth;
    private float maxWidth;

    private void onChildViewCountChanged() {
        int childCount = getChildCount();
        float scale = minWidthScale;

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            float origin_width = view.getWidth() == 0 ? this.width : view.getWidth();
            float origin_height = view.getHeight() == 0 ? this.height : view.getHeight();
            int origin_top = view.getTop() == 0 ? getPaddingTop() : view.getTop() - getPaddingTop();
            float target_width = this.width * scale;
            float target_height = this.height * scale;
            float target_top = 0;
            if (i > childCount - 4) {//最后三张
                target_top = (i - childCount + 3) * unitMarginTop;// (i - childCount + 3) => 0 1 2
            }
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
            animator.setDuration(250);
            float finalTarget_top = target_top;
            animator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                lp.width = Math.round(origin_width + (target_width - origin_width) * value);
                lp.height = Math.round(origin_height + (target_height - origin_height) * value);
                lp.topMargin = Math.round(origin_top + (finalTarget_top - origin_top) * value);

                view.setTag(R.id.key_tag_position, lp.topMargin + getPaddingTop());
                view.setLayoutParams(lp);
            });
            animator.start();
            if (i > childCount - 4) { //最后三张
                scale = scale * 1.1f;
            }
        }
    }


    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        private ValueAnimator flingAnimator;
        private View targetView;

        @Override
        public boolean onDown(MotionEvent e) {
            stopFlingAnim();
            int childCount = getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                View view = getChildAt(i);
                if (ViewUtil.isTouchPointInView(view, e.getRawX(), e.getRawY())) {
                    targetView = view;
                    break;
                } else {
                    targetView = null;
                }
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int pointerCount = e2.getPointerCount();
            if (pointerCount > 1) {
                return false;
            }
            horOrVerScroll(distanceX, distanceY);
            return true;
        }

        private void horOrVerScroll(float distanceX, float distanceY) {
            if (orientation == TouchEventHelper.orientation_horizontal) {
                horizontalScroll(distanceX);
            }
            if (orientation == TouchEventHelper.orientation_vertical) {
                verticalScroll(distanceY);
            }
        }

        private void onUp() {
            onHorScrollEnd();
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (orientation == TouchEventHelper.orientation_vertical) {
                fling(velocityY);
            }
            return true;
        }


        private void horizontalScroll(float distanceDX) {
            if (targetView != null) {
                targetView.offsetLeftAndRight(Math.round(-distanceDX));
            }
        }

        private void verticalScroll(float distanceDY) {
            if (targetView != null) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = getChildAt(i);
                    float newDy = distanceDY * ((i + 1f) / childCount);
                    LayoutParams lp = (LayoutParams) view.getLayoutParams();
                    lp.topMargin = lp.topMargin + Math.round(-newDy);
                    float minTopMargin = 0;
                    float maxTopMargin = height * 10;
                    if (i > childCount - 4) {//最后三张
                        minTopMargin = (i - childCount + 3) * unitMarginTop;// (i - childCount + 3) => 0 1 2
                    }
                    lp.topMargin = Math.round(MathUtil.clamp(lp.topMargin, minTopMargin, maxTopMargin));
                 //   float scale = MathUtil.clamp(lp.topMargin / (float) lp.height, 0, 1);// 0 -> 1
                 //   lp.width = Math.round(minWidth + (maxWidth - minWidth) * scale);
                    view.setLayoutParams(lp);
                }
            }
        }

        private void fling(float velocity) {
            int flingDuring = 200;
            float distanceX = -velocity * (flingDuring / 2000f);
            stopFlingAnim();
            flingAnimator = ValueAnimator.ofFloat(distanceX, 0);
            flingAnimator.setDuration(flingDuring);
            flingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                private float lastTargetDistance;

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                    if (lastTargetDistance != 0) {
                        float distance = value - lastTargetDistance;
                        horOrVerScroll(0, -distance);
                    }
                    lastTargetDistance = value;
                }
            });
            flingAnimator.start();
        }

        private void stopFlingAnim() {
            if (flingAnimator != null) {
                flingAnimator.end();
                flingAnimator.cancel();
            }
        }

        private void onHorScrollEnd() {
            if (orientation == TouchEventHelper.orientation_horizontal && targetView != null) {
                int currentLeft = targetView.getLeft();
                int left = Math.round((getWidth() - targetView.getWidth()) / 2f);
                if (currentLeft < -targetView.getWidth() / 5) {
                    left = -targetView.getWidth() - Math.round(dp_1 * 5);
                }
                if (currentLeft > getWidth() - targetView.getWidth() / 5 * 4) {
                    left = getWidth() + Math.round(dp_1 * 5);
                }
                float dx = left - currentLeft;
                ValueAnimator animator = ValueAnimator.ofFloat(dx, 0);
                animator.setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    private float lastTargetDistance;

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                        if (lastTargetDistance != 0) {
                            float distance = value - lastTargetDistance;
                            horOrVerScroll(distance, 0);
                        }
                        lastTargetDistance = value;
                        if (value == 0) {
                            onScrollEnd();
                        }
                    }
                });
                animator.start();
            }
        }

        private void onScrollEnd() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if (view == null) return;
                int left = view.getLeft();
                if (left <= -view.getWidth() || left >= getWidth()) {
                    removeView(view);
                    onChildViewCountChanged();
                }
            }
        }
    }

}
