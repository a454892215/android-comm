package com.test.util.custom_view.fragment.dialog;


import com.common.R;
import com.common.base.BaseActivity;
import com.common.base.BasePop;

public class DropPop extends BasePop {

    public DropPop(BaseActivity activity) {
        super(activity);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.drop_pop;
    }


    @Override
    protected void initView() {
    }
}
