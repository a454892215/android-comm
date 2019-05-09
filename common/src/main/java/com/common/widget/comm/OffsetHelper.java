package com.common.widget.comm;

import android.animation.ValueAnimator;
import android.view.View;

public class OffsetHelper {

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
}
