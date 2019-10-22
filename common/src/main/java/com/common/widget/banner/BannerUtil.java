package com.common.widget.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

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
