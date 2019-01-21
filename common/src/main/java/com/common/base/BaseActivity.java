package com.common.base;

import android.content.Intent;
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
    private boolean isShowing;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(getLayoutId());
        Intent intent = getIntent();
        if (intent != null) {
            String titles = intent.getStringExtra("title");
            setTitle(titles);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
    }

    public boolean isShowing() {
        return isShowing;
    }

    protected abstract int getLayoutId();
}
