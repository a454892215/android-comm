package com.common.widget.banner;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.List;

public class BannerUtil {

    public static void initBanner(Banner banner, List<String> urlList) {
        banner.setImageLoader((Banner.ImageLoader) (context, path, imageView) ->
                Glide.with(banner.getContext()).load(path).into(imageView));
        banner.setImageUrls(urlList);
        banner.start();
    }

    public static void setIndicator(Banner banner, ViewGroup parent, int indicatorLayoutId, int indicatorCount) {
        for (int i = 0; i < indicatorCount; i++) {
            LayoutInflater.from(parent.getContext()).inflate(indicatorLayoutId, parent, true);
        }
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorCount; i++) {
                    parent.getChildAt(i).setSelected(false);
                }
                parent.getChildAt(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
