package com.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.common.R;
import com.common.utils.LogUtil;


public abstract class BaseDialogFragment extends DialogFragment {

    protected BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.comm_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        getDialog().setCanceledOnTouchOutside(false);
      //  getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        View rootView = inflater.inflate(getLayoutId(), container, false);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
           // lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            lp.dimAmount = 0f;
            window.setAttributes(lp);
        } else {
            LogUtil.e("==========window == null =======");
        }
        initView(rootView);
        return rootView;
    }

    protected Object param;

    public void setParam(Object param) {
        this.param = param;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View rootView);

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }
}
