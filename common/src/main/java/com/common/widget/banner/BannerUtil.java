package com.common.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class BannerUtil {

    public static void initBanner(Banner banner, List<String> urlList) {
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(banner.getContext()).load(path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片集合
        banner.setImages(urlList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}
