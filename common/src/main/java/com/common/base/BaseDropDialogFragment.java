package com.common.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.common.R;

public class BaseDropDialogFragment extends BaseDialogFragment {

    public BaseDropDialogFragment(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_drop);
        setHeight(wrap_content);
        setWidth(wrap_content);
        setDimeAmount(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_drop_test;
    }

    public void showAsDropDown(View anchorView, int left, int top) {
        setGravity(Gravity.TOP | Gravity.START);
        int[] location_anchor = new int[2];
        anchorView.getLocationOnScreen(location_anchor);
        y = location_anchor[1] + top;
        x = location_anchor[0] + left;
        show(activity.fm, getClass().getName());
    }

    @Override
    protected void initView() {
    }
}
