package com.common.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.common.R;
import com.common.base.BasePopWindow;


public class LoadingPopWindow extends BasePopWindow {


    public LoadingPopWindow(Activity activity) {
        super(activity, false);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55000000")));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_loading_layout;
    }

    @Override
    protected void initView() {

    }


}
