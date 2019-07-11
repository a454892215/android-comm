package com.common.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.common.R;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    protected BaseActivity activity;
    protected FragmentManager fm;
    protected float dp_1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            activity = (BaseActivity) getActivity();
            rootView = inflater.inflate(getLayoutId(), container, false);
            dp_1 = rootView.getResources().getDimension(R.dimen.dp_1);
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
