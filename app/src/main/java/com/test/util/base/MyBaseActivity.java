package com.test.util.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.comm.timer.TimerTest;
import com.jaeger.library.StatusBarUtil;
import com.test.util.Constant;
import com.test.util.R;

public abstract class MyBaseActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this,0,null);
        ImageView iv_back = findViewById(R.id.iv_header_back);
        if (iv_back != null) {
            iv_back.setOnClickListener(this);
        }
        String title = getIntent().getStringExtra(Constant.KEY_HEADER_TITLE);
        if (!TextUtils.isEmpty(title)) setTitle(title);
        TimerTest.testFPS(this, 60);

    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tv_header_title = findViewById(R.id.tv_header_title);
        if (tv_header_title != null) {
            tv_header_title.setText(title);
        } else {
            super.setTitle(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_back:
                onBackPressed();
                break;
        }
    }
}
