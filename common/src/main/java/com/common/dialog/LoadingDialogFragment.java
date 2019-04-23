package com.common.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.common.R;
import com.common.base.BaseDialogFragment;

public class LoadingDialogFragment extends BaseDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_center);
        setDimeAmount(0.7f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_loading_layout;
    }

    @Override
    protected void initView() {

    }


}
