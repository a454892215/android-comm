package com.common.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.common.R;

import java.util.ArrayList;
import java.util.List;

public class DragFloatFrameLayout extends FrameLayout {
    private int parentHeight;
    private int parentWidth;
    private int lastX;
    private int lastY;
    private boolean isDrag;
    private float dp_1;

    public DragFloatFrameLayout(Context context) {
        super(context);
    }

    public DragFloatFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dp_1 = getResources().getDimension(R.dimen.dp_1);
    }

    int down_X = 0;
    int down_Y = 0;
    long down_time = 0;

    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (event.getAction() & 255) {
            case MotionEvent.ACTION_DOWN:
                down_time = System.currentTimeMillis();
                down_X = rawX;
                down_Y = rawY;
                this.setPressed(true);
                this.isDrag = false;
                this.getParent().requestDisallowInterceptTouchEvent(true);
                this.lastX = rawX;
                this.lastY = rawY;
                if (this.getParent() != null) {
                    ViewGroup parent = (ViewGroup) this.getParent();
                    this.parentHeight = parent.getHeight();
                    this.parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_UP:
                long clickTime = System.currentTimeMillis() - down_time;
                boolean clickCondition_1 = clickTime < 200 && Math.abs(rawX - down_X) < dp_1 * 5 && Math.abs(rawY - down_Y) < dp_1 * 5;
                boolean clickCondition_2 = Math.abs(rawX - down_X) == 0 && Math.abs(rawY - down_Y) == 0;
                if (clickCondition_1 || clickCondition_2) {
                    performClick();
                }
                if (this.isDragging()) {
                    this.setPressed(false);
                    this.moveHide();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ((double) this.parentHeight > 0.2D && (double) this.parentWidth > 0.2D) {
                    this.isDrag = true;
                    int dx = rawX - this.lastX;
                    int dy = rawY - this.lastY;
                    int distance = (int) Math.sqrt((double) (dx * dx + dy * dy));
                    if (distance == 0) {
                        this.isDrag = false;
                    } else {
                        float x = this.getX() + (float) dx;
                        float y = this.getY() + (float) dy;
                        x = x < 0.0F ? 0.0F : (x > (float) (this.parentWidth - this.getWidth()) ? (float) (this.parentWidth - this.getWidth()) : x);
                        y = this.getY() < 0.0F ? 0.0F : (this.getY() + (float) this.getHeight() > (float) this.parentHeight ? (float) (this.parentHeight - this.getHeight()) : y);
                        this.setX(x);
                        this.setY(y);
                        this.lastX = rawX;
                        this.lastY = rawY;
                    }
                } else {
                    this.isDrag = false;
                }
        }

        return true;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private boolean isDragging() {
        return this.isDrag || this.getX() != 0.0F || this.getX() != this.parentWidth - this.getWidth()
                || this.getY() != 0.0F || this.getY() != this.parentWidth - this.getHeight();
    }

    List<DistanceEntity> distanceList = new ArrayList<>();

    private void moveHide() {
        distanceList.clear();
        distanceList.add(new DistanceEntity(getX(), "x", 0, getX()));
        distanceList.add(new DistanceEntity(getX(), "x", parentWidth - this.getWidth(), parentWidth - getX() - getWidth()));
        distanceList.add(new DistanceEntity(getY(), "y", 0, getY()));
        distanceList.add(new DistanceEntity(getY(), "y", parentHeight - this.getHeight(), parentHeight - getY() - getHeight()));
        DistanceEntity minValuePosition = getMinDistanceEntity(distanceList);
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, minValuePosition.target, minValuePosition.start, minValuePosition.end);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500L);
        animator.start();
    }

    private DistanceEntity getMinDistanceEntity(List<DistanceEntity> distanceList) {
        DistanceEntity minDistance = distanceList.get(0);
        for (int i = 1; i < distanceList.size(); i++) {
            minDistance = distanceList.get(i).distance < minDistance.distance ? distanceList.get(i) : minDistance;
        }
        return minDistance;
    }


    private static class DistanceEntity {
        String target;
        float start;
        float end;
        float distance;

        DistanceEntity(float start, String target, float end, float distance) {
            this.start = start;
            this.target = target;
            this.end = end;
            this.distance = distance;
        }
    }
}

