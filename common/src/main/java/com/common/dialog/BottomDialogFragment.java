package com.common.dialog;

import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.base.BaseDialogFragment;

public class BottomDialogFragment extends BaseDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_bottom).setGravity(Gravity.BOTTOM);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_test;
    }

    @Override
    protected void initView() {

    }
}
