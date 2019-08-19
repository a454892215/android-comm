package com.common.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Author:  L
 * Description: No
 */
@SuppressWarnings("unused")
public class ViewAnimUtil {

    public static void startBgColorAnim(View view, int startColor, int endColor, int during) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        valueAnimator.setDuration(during);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            view.setBackgroundColor(value);
        });
        valueAnimator.start();
    }

    public static void startDownOpenAnim1(View view, int during) {
        view.measure(0, 0);
        view.setTranslationY(-view.getMeasuredHeight());
        view.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(during).start();
    }

    public static void startDownOpenAnim2(View view, int during) {
        view.animate().translationY(-view.getHeight()).setInterpolator(new AccelerateInterpolator()).setDuration(during).start();
    }
}
