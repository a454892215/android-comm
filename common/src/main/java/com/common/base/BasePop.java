package com.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;


public abstract class BasePop {

    private View rootView;
    private ViewGroup contentView;

    public BasePop(BaseActivity activity) {
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
        }
        initView();
    }

    protected BaseActivity activity;

    @SuppressWarnings("unused")
    public void show() {
        if (rootView.getParent() == null) {
            contentView.addView(rootView);
            updateView();
        }
    }

    public void showAsDropDown(View anchorView) {
        if (rootView.getParent() == null) {
            int[] location_anchor = new int[2];
            int[] location_content = new int[2];
            anchorView.getLocationOnScreen(location_anchor);
            contentView.getLocationOnScreen(location_content);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
            lp.topMargin = location_anchor[1] + anchorView.getHeight() - location_content[1];
            contentView.addView(rootView, lp);
            updateView();
        }
    }

    public void dismiss() {
        ViewParent parent = rootView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(rootView);
        }
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

}
