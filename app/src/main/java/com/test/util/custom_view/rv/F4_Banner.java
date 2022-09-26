package com.test.util.custom_view.rv;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.common.base.BaseFragment;
import com.common.widget.banner.Banner;
import com.common.widget.banner.BannerLayout;
import com.test.util.R;
import com.test.util.sticky.Fragment__sticky_01;

import java.util.Arrays;

public class F4_Banner extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test4;
    }

    private static final Integer[] picIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    @Override
    protected void initView() {
        Banner banner = findViewById(R.id.banner);
        banner.setImageLoader(new MyImageLoader());
        banner.notifyDataChange(picIds);
    }

    private class MyImageLoader implements Banner.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(F4_Banner.this).load(path).into(imageView);
        }
    }
}
