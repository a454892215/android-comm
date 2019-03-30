package com.test.util.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.test.util.R;

public abstract class MyBaseActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView iv_back = findViewById(R.id.iv_header_back);
        if (iv_back != null) {
            iv_back.setOnClickListener(this);
        }
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
