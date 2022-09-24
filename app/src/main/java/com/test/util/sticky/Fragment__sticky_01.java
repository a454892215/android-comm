package com.test.util.sticky;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.base.BaseFragment;
import com.common.utils.LogUtil;
import com.common.widget.banner.Banner;
import com.test.util.R;
import com.test.util.comm.SimpleTextAdapter;
import com.test.util.comm.TestDataHelper;

public class Fragment__sticky_01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_sticky_01;
    }

    private static final Integer[] picIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter adapter = new SimpleTextAdapter(activity, TestDataHelper.getData(100));
        rv.setAdapter(adapter);

        Banner banner = findViewById(R.id.banner);
        banner.setImageLoader(new MyImageLoader());
        banner.notifyDataChange(picIds);
    }


    private class MyImageLoader implements Banner.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(Fragment__sticky_01.this).load(path).into(imageView);
        }
    }

    private static class MyRequestListener implements RequestListener<Drawable> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            LogUtil.e("onLoadFailed");
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            LogUtil.d("onResourceReady");
            return false;
        }
    }

}
