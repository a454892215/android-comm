package com.common.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.common.R;

public class BaseDropDialogFragment extends BaseDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_drop);
        setHeight(wrap_content);
        setWidth(wrap_content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_drop_test;
    }

    public void showAsDropDown(FragmentManager fm, String tag, View anchorView, int left) {
        setGravity(Gravity.TOP | Gravity.START);
        int[] location_anchor = new int[2];
        anchorView.getLocationOnScreen(location_anchor);
        y = location_anchor[1];
        x = location_anchor[0];
        show(fm, tag);
    }


    @Override
    protected void initView() {
    }
}
