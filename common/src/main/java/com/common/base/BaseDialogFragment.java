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
import com.common.hotfix.HotFixEvent;
import com.common.utils.LogUtil;


public abstract class BaseDialogFragment extends DialogFragment {

    protected BaseActivity activity;
    private View rootView;
    private boolean canceledOnTouchOutside = true;
    private float dimeAmount = 0.7f;
    private int width = WindowManager.LayoutParams.WRAP_CONTENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;
    protected int match_parent = WindowManager.LayoutParams.MATCH_PARENT;
    protected int wrap_content = WindowManager.LayoutParams.WRAP_CONTENT;
    private int animStyle;
    protected float dp_1;

    protected int x = 0;
    protected int y = 0;
    private Window window;
    protected FragmentManager fm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.comm_dialog);
        activity = (BaseActivity) getActivity();
        fm = getChildFragmentManager();
        if (activity != null) {
            dp_1 = activity.getResources().getDimension(R.dimen.dp_1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded()) {
            super.show(manager, tag);
            HotFixEvent.onShowDialogFragment(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (window != null) {
            window.setWindowAnimations(R.style.dialog_anim_default); //禁止恢复屏幕时候动画
        }

    }

    private void setWindow() {
        window = getDialog().getWindow();
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
            lp.x = x;
            lp.y = y;
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    protected BaseDialogFragment setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public View getRootView() {
        return rootView;
    }



    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

}
