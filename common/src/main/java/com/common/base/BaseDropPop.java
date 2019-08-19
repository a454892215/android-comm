package com.common.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.common.utils.ViewAnimUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class BaseDropPop {

    protected ViewGroup rootView;
    private ViewGroup contentView;
    private FrameLayout bg_transition_view;
    private View drop_content_view;

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
            rootView = new FrameLayout(activity);
            bg_transition_view = new FrameLayout(activity);
            rootView.addView(bg_transition_view);
            drop_content_view = LayoutInflater.from(activity).inflate(getLayoutId(), bg_transition_view, false);
            bg_transition_view.addView(drop_content_view);

        }
        rootView.setOnTouchListener((v, event) -> {
            if (dismissEnableOnTouchOutside && event.getAction() == MotionEvent.ACTION_UP) {
                dismiss();
            }
            return true;
        });
        bg_transition_view.setOnTouchListener((v, event) -> true);
        initView();
    }

    protected BaseActivity activity;

    public void showAsDropDown(View anchorView, int left, int top) {
        if (rootView.getParent() == null) {
            int[] location_anchor = new int[2];
            int[] location_content = new int[2];
            anchorView.getLocationOnScreen(location_anchor);
            contentView.getLocationOnScreen(location_content);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) bg_transition_view.getLayoutParams();
            lp.topMargin = location_anchor[1] + anchorView.getHeight() - location_content[1] + top;
            lp.leftMargin = left;
            bg_transition_view.setLayoutParams(lp);
            contentView.addView(rootView);
            startEnterAnim();
        }
    }

    public void dismiss() {
        ViewParent parent = rootView.getParent();
        if (parent instanceof ViewGroup) {
            startExitAnim();
            if (dismissListenerList.size() > 0) {
                for (int i = 0; i < dismissListenerList.size(); i++) {
                    OnDismissListener onDismissListener = dismissListenerList.get(i);
                    onDismissListener.onDismiss();
                }
            }
            rootView.postDelayed(() -> ((ViewGroup) parent).removeView(rootView), 300);

        }
    }

    private void startEnterAnim() {
        ViewAnimUtil.startBgColorAnim(bg_transition_view, 0x00000000, 0xaa000000, 300);
        ViewAnimUtil.startDownOpenAnim1(drop_content_view, 300);
    }

    private void startExitAnim() {
        ViewAnimUtil.startBgColorAnim(bg_transition_view, 0xaa000000, 0x00000000, 300);
        ViewAnimUtil.startDownOpenAnim2(drop_content_view, 300);
    }

    public boolean isShowing() {
        return rootView != null && rootView.getParent() != null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }


    private boolean dismissEnableOnTouchOutside = true;

    public void setDismissEnableOnTouchOutside(boolean enable) {
        dismissEnableOnTouchOutside = enable;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    private List<OnDismissListener> dismissListenerList = new ArrayList<>();

    public void addOnDismissListener(OnDismissListener onDismissListener) {
        dismissListenerList.add(onDismissListener);
    }
}
