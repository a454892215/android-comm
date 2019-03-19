package com.common.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author:  Pan
 * Description: No
 */
@SuppressWarnings("unused")
public class ViewUtil {
    public static boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }

    public static void onlyShowOneChildView(ViewGroup parentView, int showingPosition) {
        int childCount = parentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parentView.getChildAt(i);
            if (showingPosition == i) {
                childView.setVisibility(View.VISIBLE);
            } else {
                childView.setVisibility(View.GONE);
            }
        }
    }

    public static void showAndGoneView(View showingView, View... goneView) {
        showingView.setVisibility(View.VISIBLE);
        for (View aGoneView : goneView) {
            aGoneView.setVisibility(View.GONE);
        }
    }

    public static void switchViewVisibilityState(View... views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void switchViewVisibilityStateWithAnim(View... views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                ScaleAnimation animation = new ScaleAnimation(1, 1, 1, 0);
                animation.setDuration(200);
                view.setAnimation(animation);
                view.postDelayed(() -> view.setVisibility(View.GONE), 200);

            } else {
                view.setVisibility(View.VISIBLE);
                ScaleAnimation animation = new ScaleAnimation(1, 1, 0, 1);
                animation.setDuration(200);
                view.setAnimation(animation);
            }
        }
    }

    public static void setLinearLayoutGoneTopMargin(int topMargin, int bottomMargin, View view) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (view.getVisibility() == View.VISIBLE) {
            lp.topMargin = topMargin;
            lp.bottomMargin = bottomMargin;
        } else {
            lp.topMargin = 0;
            lp.bottomMargin = 0;
        }
        view.setLayoutParams(lp);
    }

    public static void clipRoundRectView(View view, float radius) {
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                outline.setRoundRect(rect, radius);
            }
        });
    }

    public static void setGrayImage(ImageView imageView) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter matrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(matrixColorFilter);

    }

}
