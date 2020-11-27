package com.test.util.sticky;

import android.graphics.drawable.Drawable;
import android.view.View;

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
import com.common.widget.banner.BannerLayout;
import com.common.widget.banner.BannerUtil;
import com.test.util.R;
import com.test.util.comm.SimpleTextAdapter;
import com.test.util.comm.TestDataHelper;

import java.util.Arrays;

public class Fragment__sticky_01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_sticky_01;
    }

    private static String[] imgUrl = {"http://img17.3lian.com/d/file/201702/21/8f8a5c670f68613382cb043d1ad2fe05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/1fa7ef2fbf14cb7640ea50de1914cd05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/44b2c79be750dcc69f919bc786cbd173.jpg"
            , "http://img17.3lian.com/d/file/201702/21/834c9af2d7b02b74a1d9d44b527c53ff.jpg"
            , "http://img17.3lian.com/d/file/201702/21/8c49c4da75a889cc3c4ceb211a2adaa3.jpg"};
    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter adapter = new SimpleTextAdapter(activity, TestDataHelper.getData(100));
        rv.setAdapter(adapter);

        Banner banner = findViewById(R.id.banner);
        banner.setImageLoader((Banner.ImageLoader) (context, path, imageView) ->
                Glide.with(banner.getContext()).load(path).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtil.e(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView));
        banner.notifyDataChange( Arrays.asList(imgUrl));
    }

}
