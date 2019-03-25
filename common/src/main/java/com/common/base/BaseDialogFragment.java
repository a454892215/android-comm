package com.common.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialogFragment extends DialogFragment {

    private View rootView;
    protected BaseActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            activity = (BaseActivity) getActivity();
            rootView = inflater.inflate(getLayoutId(), container, false);
            initView(rootView);
        }
        return rootView;
    }

    protected Object param;

    public void setParam(Object param) {
        this.param = param;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View rootView);
}
