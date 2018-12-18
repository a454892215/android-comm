package com.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Author:  Liu Pan
 * CreateDate: 2018/12/17 16:29
 * Description: No
 */

public abstract class BaseActivity extends AppCompatActivity {
   protected BaseActivity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();
}
