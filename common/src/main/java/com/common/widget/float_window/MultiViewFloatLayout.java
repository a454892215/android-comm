package com.common.widget.float_window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;
import com.common.utils.MathUtil;
import com.common.utils.ViewUtil;
import com.common.widget.comm.TouchEventHelper;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class MultiViewFloatLayout extends FrameLayout {

    private float dp_1;
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
        postDelayed(() -> height = getHeight(), 200);
        touchEventHelper = new TouchEventHelper(context);
        touchEventHelper.setOnClickListener(param -> {
            if (isWindowMode) switchWindowMode();
        });
        simpleGestureListener = new SimpleGestureListener();
        gestureDetector = new GestureDetector(context, simpleGestureListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        touchEventHelper.getTouchOrientation(event, ori -> orientation = ori);
        boolean isResume = true;
        if (!isWindowMode) {
            isResume = super.dispatchTouchEvent(event);
        } else {
            onTouchEvent(event);
        }
        return isResume;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isWindowMode) {
                simpleGestureListener.onUp();
            }
        }
        if (isWindowMode) {
            gestureDetector.onTouchEvent(event);
        }
        return true;
    }

    private static final float minWidthScale = 0.8f;

    private boolean isWindowMode = false;

    private void openWindowMode() {
        int childCount = getChildCount();
        float scale = minWidthScale;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            // float target_top = 0;
            // if (i > childCount - 4) target_top = (i - childCount + 3) * unitMarginTop;
            view.animate().setDuration(200).scaleX(scale).start();
            view.animate().setDuration(200).scaleY(scale).start();
            // view.animate().setDuration(200).translationY(target_top).start();
            // view.setTag(R.id.key_tag_position, target_top + getPaddingTop());
            if (i > childCount - 4) scale = scale * 1.1f; // (i - childCount + 3) => 0 1 2 last 3
        }
        postDelayed(() -> isWindowMode = true, 220);
    }

    public void closeWindowMode() {
        int childCount = getChildCount();
        if (childCount > 0) {
            View selectedView = getChildAt(childCount - 1);//获取最上面的View
            selectedView.animate().setDuration(200).scaleX(1).start();
            selectedView.animate().setDuration(200).scaleY(1).start();
            selectedView.animate().setDuration(200).translationY(0).start();
            postDelayed(() -> isWindowMode = false, 270);

        }
    }

    public void switchWindowMode() {
        if (isWindowMode) {
            closeWindowMode();
        } else {
            openWindowMode();
        }
    }

    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        private View targetView;

        @Override
        public boolean onDown(MotionEvent e) {
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
            if (orientation == TouchEventHelper.orientation_horizontal && targetView != null) {
                targetView.setTranslationX(targetView.getTranslationX() - distanceX);
            }
            if (orientation == TouchEventHelper.orientation_vertical) {
                verticalScroll(distanceY);
            }
            return true;
        }

        private void onUp() {
            onHorScrollEnd();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (orientation == TouchEventHelper.orientation_vertical) {
                final int flingDuring = 200;
                float distanceY = velocityY * (flingDuring / 2000f);
                if (targetView != null) {
                    targetView.animate().setDuration(flingDuring).translationY(distanceY).start();
                }
            }
            return true;
        }

        private void verticalScroll(float distanceDY) {
            if (targetView != null) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = getChildAt(i);
                    float newDy = distanceDY * ((i + 1f) / childCount);
                    float transY = view.getTranslationY() + Math.round(-newDy);
                    float minTopMargin = -dp_1 * 300;
                    float maxTopMargin = height * 10;
                    view.setTranslationY(MathUtil.clamp(transY, minTopMargin, maxTopMargin));
                }
            }
        }

        private void onHorScrollEnd() {
            if (orientation == TouchEventHelper.orientation_horizontal && targetView != null) {
                float translationX = targetView.getTranslationX();
                float left = 0;
                if (translationX < -targetView.getWidth() / 2) {
                    left = -targetView.getWidth();
                }
                if (translationX > targetView.getWidth() / 2) {
                    left = targetView.getWidth();
                }
                targetView.animate().setDuration(200).translationX(left).start();
                postDelayed(this::onScrollEnd, 220);
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
                    openWindowMode();
                }
            }
        }
    }

}
