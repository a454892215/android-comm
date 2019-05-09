package com.common.widget.float_window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.common.utils.LogUtil;
import com.common.utils.ViewUtil;
import com.common.widget.comm.OffsetHelper;
import com.common.widget.comm.TouchEventHelper;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class MultiViewFloatLayout extends FrameLayout {

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
        touchEventHelper = new TouchEventHelper(context);
        touchEventHelper.setOnClickListener(param -> {
            if (isWindowMode) switchWindowMode((MotionEvent) param);
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

    private static final float minWidthScale = 0.88f;

    private boolean isWindowMode = false;

    private void openWindowMode() {
        int childCount = getChildCount();
        float scale = minWidthScale;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.animate().setDuration(200).scaleX(scale).start();
            view.animate().setDuration(200).scaleY(scale).start();
            if (i > childCount - 4) scale = scale * 0.92f; // (i - childCount + 3) => 0 1 2 last 3
            if (scale <= 0.6) scale = 0.6f;
        }
        postDelayed(() -> isWindowMode = true, 220);
    }

    public void closeWindowMode() {
        int childCount = getChildCount();
        if (childCount > 0) {
            View selectedView;
            if (targetView != null) {
                selectedView = targetView;
            } else {
                selectedView = getChildAt(childCount - 1);//获取最上面的View
            }
            selectedView.bringToFront();
            selectedView.animate().setDuration(200).scaleX(1).start();
            selectedView.animate().setDuration(200).scaleY(1).start();
            postDelayed(() -> isWindowMode = false, 270);

        }
    }

    public void switchWindowMode(MotionEvent ev) {
        if (isWindowMode) {
            setTargetView(ev);
            closeWindowMode();
        } else {
            openWindowMode();
        }
    }

    private void setTargetView(MotionEvent e) {
        if (e == null) return;
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
    }

    private View targetView;

    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            setTargetView(e);
            return true;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int pointerCount = e2.getPointerCount();
            if (pointerCount > 1) {
                return false;
            }
            if (orientation == TouchEventHelper.orientation_horizontal && targetView != null) {
                targetView.animate().setDuration(0).translationXBy(-distanceX).start();
            }
            if (orientation == TouchEventHelper.orientation_vertical && targetView != null) {
                LogUtil.d("================getTop:" + targetView.getTop());
                targetView.offsetTopAndBottom(-(int) distanceY);

                int childCount = getChildCount();
                for (int i = childCount - 1; i > -1; i--) {
                    View child = getChildAt(i);
                }
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
                float dy = velocityY * (flingDuring / 2000f);
                if (targetView != null) {
                    OffsetHelper.offsetTopAndBottom(targetView, dy, flingDuring);
                }
            }
            return true;
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
                float translationX = view.getTranslationX();
                if (translationX <= -view.getWidth() || translationX >= getWidth()) {
                    removeView(view);
                    openWindowMode();
                }
            }
        }
    }

}
