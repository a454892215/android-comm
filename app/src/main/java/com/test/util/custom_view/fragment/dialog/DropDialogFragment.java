package com.test.util.custom_view.fragment.dialog;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.common.R;
import com.common.base.BaseDialogFragment;

public class DropDialogFragment extends BaseDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_top);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_drop_test;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    @Override
    protected void initView() {
    }
}
