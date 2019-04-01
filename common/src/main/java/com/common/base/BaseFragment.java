package com.common.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    protected BaseActivity activity;
    protected FragmentManager fm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            activity = (BaseActivity) getActivity();
            rootView = inflater.inflate(getLayoutId(), container, false);
            fm = getChildFragmentManager();
            initView();
        }
        return rootView;
    }

    protected Object param;

    @SuppressWarnings("unused")
    public void setParam(Object param) {
        this.param = param;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }
}
