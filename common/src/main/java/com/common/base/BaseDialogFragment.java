package com.common.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.common.R;
import com.common.utils.LogUtil;


public abstract class BaseDialogFragment extends DialogFragment {

    protected BaseActivity activity;
    private View rootView;
    private boolean canceledOnTouchOutside = true;
    private float dimeAmount = 0.7f;
    private int width = WindowManager.LayoutParams.WRAP_CONTENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;

    private int animStyle;
    protected FragmentManager fm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.comm_dialog);
        fm = getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        getDialog().setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) parent.removeView(rootView);
        }
        setWindow();
        return rootView;
    }

    private void setWindow() {
        Window window = getDialog().getWindow();
        //  getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = gravity;
            lp.width = width;
            lp.height = height;
            // lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            lp.dimAmount = dimeAmount;
            window.setAttributes(lp);
            if (animStyle > 0) {
                window.setWindowAnimations(animStyle);
            }
        } else {
            LogUtil.e("==========window == null =======");
        }
    }

    protected Object param;

    @SuppressWarnings("unused")
    public void setParam(Object param) {
        this.param = param;
    }

    @SuppressWarnings("unused")
    protected BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    protected BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    @SuppressWarnings("unused")
    protected BaseDialogFragment setDimeAmount(float dimeAmount) {
        this.dimeAmount = dimeAmount;
        return this;
    }

    @SuppressWarnings("unused")
    protected BaseDialogFragment setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public BaseDialogFragment setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

}
