package com.common.utils;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.common.R;

/**
 * Author:  Pan
 * Description: No
 */

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

    public static void showAndGoneView(View showingView, View... goneView) {
        showingView.setVisibility(View.VISIBLE);
        for (View aGoneView : goneView) {
            aGoneView.setVisibility(View.GONE);
        }
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

}
