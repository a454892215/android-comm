package com.common.dialog;

import android.app.Activity;
import android.content.Context;

import com.common.R;
import com.common.base.BasePopWindow;


public class LoadingPopWindow extends BasePopWindow {


    public LoadingPopWindow(Context context, Activity activity, boolean outsideTouchable) {
        super(context, activity, outsideTouchable);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_loading_layout;
    }

    @Override
    protected void initView() {

    }


}
