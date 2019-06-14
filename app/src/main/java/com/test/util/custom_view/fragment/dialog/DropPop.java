package com.test.util.custom_view.fragment.dialog;


import com.common.R;
import com.common.base.BaseActivity;
import com.common.base.BaseDropPop;

public class DropPop extends BaseDropPop {

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

    @Override
    protected void updateView() {
        super.updateView();
    }

    @Override
    protected void startEnterAnim() {
        super.startEnterAnim();
    }

    @Override
    protected void startExitAnim() {
        super.startExitAnim();
    }
}
