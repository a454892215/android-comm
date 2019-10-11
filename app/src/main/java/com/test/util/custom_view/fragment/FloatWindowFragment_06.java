package com.test.util.custom_view.fragment;


import com.common.base.BaseFragment;
import com.common.widget.float_window.MultiViewFloatLayout;
import com.test.util.R;

public class FloatWindowFragment_06 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_float_window;
    }

    @Override
    protected void initView() {
        MultiViewFloatLayout multi_view = findViewById(R.id.multi_view);
        findViewById(R.id.btn).setOnClickListener(v -> multi_view.switchWindowMode(null));
    }
}
