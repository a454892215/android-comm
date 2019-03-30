package com.common.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.common.R;
import com.common.base.BaseDialogFragment;

public class CenterDialogFragment extends BaseDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_center);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_test;
    }

    @Override
    protected void initView() {

    }


}
