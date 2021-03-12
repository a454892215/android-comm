package com.common.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  L
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

    /**
     * 获取所有子View
     */
    public static ArrayList<View> getChildrenView(ViewGroup parent) {
        ArrayList<View> list = new ArrayList<>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            list.add(parent.getChildAt(i));
        }
        return list;
    }

    /**
     * 显示和隐藏View
     */
    public static void showAndGoneView(ViewGroup parentView, int showingPosition) {
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

    /**
     * 显示和隐藏View
     */
    public static void showAndGoneView(View showingView, View... goneView) {
        showingView.setVisibility(View.VISIBLE);
        for (View aGoneView : goneView) {
            aGoneView.setVisibility(View.GONE);
        }
    }

    /**
     * 切换View的显示状态
     */
    public static void showAndGoneView(View... views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 携带缩放动画 切换所有View的显示状态
     */
    public static void switchViewVisibleStateWithScaleAnim(View... views) {
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

    /**
     * 给 LinearLayout 的子View动态设置margin,隐藏状态margin置为0
     */
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

    /**
     * 裁剪圆角View
     */
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

    /**
     * 设置灰色图片
     */
    public static void setGrayImage(ImageView imageView) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter matrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(matrixColorFilter);
    }

    /**
     * 遍历根View 设置字体
     */
    public static void setTypefaceToAllView(View rootView, Typeface typeface) {
        List<View> allChildViews = getAllChildViews(rootView);
        for (int i = 0; i < allChildViews.size(); i++) {
            View childView = allChildViews.get(i);
            if (childView instanceof TextView) {
                int style = Typeface.NORMAL;
                Typeface typeface1 = ((TextView) childView).getTypeface();
                if (typeface1 != null) {
                    style = typeface1.getStyle();
                }
                ((TextView) childView).setTypeface(typeface, style);
            }
        }
    }

    /**
     * 递归获取所有子View 包含ViewGroup,
     */
    public static List<View> getAllChildViews(View view) {
        List<View> list = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View v = vp.getChildAt(i);
                list.add(v);
                list.addAll(getAllChildViews(v));
            }
        }
        return list;
    }

    public static float getPicHeightByWidth(Drawable drawable, float width) {
        return width * drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth();
    }

    public static Rect getScaledRect(Drawable drawable, float needWidth, float needHeight) {
        Rect rect = new Rect(0, 0, (int) needWidth, (int) needHeight);
        // 以较小比例一边为基准缩放
        float rate_origin = drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth();
        float rate = needHeight / needWidth;
        if (rate_origin > rate) { //原图height比重大 ， 以宽度为基准缩放
            rect.bottom = (int) (needWidth * drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
        } else { // 以高度为基准缩放
            rect.right = (int) (needHeight * drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight());

        }
        return rect;
    }


    public static float getBaseLine(Paint paint, float centerY) {
        return centerY - (paint.getFontMetricsInt().ascent + paint.getFontMetricsInt().descent) / 2f;
    }

    public static View addChildView(ViewGroup parentView, int layoutId) {
        View view = LayoutInflater.from(parentView.getContext()).inflate(layoutId, parentView, false);
        parentView.addView(view);
        return view;
    }

    /**
     * 获取子View在父View中相对自身的可见宽度比率
     */
    public static float getVisibleWidthRatio(ViewGroup parent, View child) {
        float ratio = 0f;
        if (child.getRight() > parent.getPaddingStart() && child.getLeft() < parent.getWidth() - parent.getPaddingEnd()) { //可见区域范围
            float visibleWidth = 0;//左边隐藏
            if (child.getLeft() < parent.getPaddingStart()) {
                visibleWidth = child.getWidth() + child.getLeft();
            }
            if (child.getRight() > parent.getWidth() - parent.getPaddingEnd()) { // 右边隐藏
                visibleWidth = child.getWidth() - (child.getRight() - (parent.getWidth() - parent.getPaddingEnd()));
            }
            ratio = visibleWidth / parent.getWidth();
        }
        return ratio;
    }
}
