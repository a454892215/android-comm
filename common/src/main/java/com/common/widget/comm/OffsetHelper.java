package com.common.widget.comm;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class OffsetHelper {

    @SuppressWarnings("unused")
    public static void offsetTopAndBottom(View view, float dy, int during) {
        ValueAnimator animator = ValueAnimator.ofFloat(dy, 0);
        animator.setDuration(during);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private float lastTargetDistance;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                if (lastTargetDistance != 0) {
                    float distanceDX = value - lastTargetDistance;
                    view.offsetTopAndBottom(Math.round(-distanceDX));
                }
                lastTargetDistance = value;
            }
        });
        animator.start();
    }

    public static void offsetTopAndBottom(ViewGroup viewGroup, float dy, int during) {
        ValueAnimator animator = ValueAnimator.ofFloat(dy, 0);
        animator.setDuration(during);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private float lastTargetDistance;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                if (lastTargetDistance != 0) {
                    float distanceDX = value - lastTargetDistance;
                    scrollAllChildViewByLevel(distanceDX, viewGroup);
                }
                lastTargetDistance = value;
            }
        });
        animator.start();
    }

    public static void scrollAllChildViewByLevel(float distanceDX, ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = childCount - 1; i > -1; i--) {
            View child = viewGroup.getChildAt(i);
            //滑动速度在一定区间和滑动距离成正比 未实现
            int newDy = Math.round(-distanceDX * ((i + 0.2f) / (childCount - 1f)));
            if (newDy + child.getTop() < 0) newDy = -child.getTop();
            child.offsetTopAndBottom(newDy);
        }
    }
}
