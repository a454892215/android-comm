package com.common.base;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.common.utils.ViewAnimUtil;

@SuppressWarnings("unused")
public abstract class BaseDropPop {

    protected View rootView;
    private ViewGroup contentView;

    private int bgColor = Color.parseColor("#aa000000");
    private View child_0;

    @SuppressLint("ClickableViewAccessibility")
    public BaseDropPop(BaseActivity activity) {
        this.activity = activity;
        activity.addOnBackPressedListener(() -> {
            if (rootView != null && rootView.getParent() != null) {
                dismiss();
                return true;
            }
            return false;
        });
        if (rootView == null) {
            contentView = activity.findViewById(android.R.id.content);
            rootView = LayoutInflater.from(activity).inflate(getLayoutId(), contentView, false);
            child_0 = ((ViewGroup) rootView).getChildAt(0);
        }
        rootView.setOnTouchListener((v, event) -> {
            if (dismissEnableOnTouchOutside && event.getAction() == MotionEvent.ACTION_UP) {
                dismiss();
            }
            return true;
        });
        child_0.setOnTouchListener((v, event) -> true);
        initView();
    }

    protected BaseActivity activity;

    public void showAsDropDown(View anchorView, int left) {
        if (rootView.getParent() == null) {
            int[] location_anchor = new int[2];
            int[] location_content = new int[2];
            anchorView.getLocationOnScreen(location_anchor);
            contentView.getLocationOnScreen(location_content);
            FrameLayout.LayoutParams child_0_lp = (FrameLayout.LayoutParams) child_0.getLayoutParams();
            child_0_lp.topMargin = location_anchor[1] + anchorView.getHeight() - location_content[1];
            child_0_lp.leftMargin = left;
            child_0.setLayoutParams(child_0_lp);
            contentView.addView(rootView);
            startEnterAnim();
            updateView();
        }
    }

    public void dismiss() {
        ViewParent parent = rootView.getParent();
        if (parent instanceof ViewGroup) {
            startExitAnim();
            rootView.postDelayed(() -> ((ViewGroup) parent).removeView(rootView), 300);

        }
    }

    private void startEnterAnim() {
        ViewAnimUtil.startBgColorAnim(rootView, Color.parseColor("#00000000"), bgColor, 300);
        child_0.startAnimation(ViewAnimUtil.getDownOpenAnim(300));
    }

    private void startExitAnim() {
        ViewAnimUtil.startBgColorAnim(rootView, bgColor, Color.parseColor("#00000000"), 300);
        child_0.startAnimation(ViewAnimUtil.getDownCloseAnim(300));
    }

    public boolean isShowing() {
        return rootView != null && rootView.getParent() != null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void updateView() {
    }

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }


    private boolean dismissEnableOnTouchOutside = true;

    public void setDismissEnableOnTouchOutside(boolean enable) {
        dismissEnableOnTouchOutside = enable;
    }
}
