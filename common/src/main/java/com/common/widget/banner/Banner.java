package com.common.widget.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.common.R;
import com.common.comm.L;
import com.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author:
 * 2020/11/15
 * Description: Banner
 */
@SuppressWarnings("unused")
public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {
    private int delayTime = 2000;
    private boolean isAutoPlay = true;
    private static final int selectedIndicatorColor = Color.WHITE;
    private static final int unselectedIndicatorColor = Color.GRAY;
    private int count = 0;
    private Context context;
    private BannerViewPager viewPager;
    private LinearLayout lltIndicatorParent;
    private ImageView bannerDefaultImage;
    private ImageLoader imageLoader;
    private BannerPagerAdapter adapter;
    private OnBannerClickListener listener;

    private WeakHandler handler = new WeakHandler();

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView(context, attrs);
        initViewPagerScroll();
    }

    private void initView(Context context, AttributeSet attrs) {
        delayTime = 2000;
        View view = LayoutInflater.from(context).inflate(R.layout.banner, this, true);
        bannerDefaultImage = view.findViewById(R.id.bannerDefaultImage);
        viewPager = view.findViewById(R.id.bannerViewPager);
        lltIndicatorParent = view.findViewById(R.id.llt_indicator_parent);
        // bannerDefaultImage.setImageResource(R.drawable.no_banner);
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            BannerScroller mScroller = new BannerScroller(viewPager.getContext());
            int scrollTime = 1000;
            mScroller.setScrollTime(scrollTime);
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }

    /**
     * 必須調用
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    private final List<Object> imageUrlList = new ArrayList<>();

    /**
     * 必須調用
     */
    public void notifyDataChange(List<?> list) {
        imageUrlList.clear();
        if (list != null) imageUrlList.addAll(list);
        this.count = imageUrlList.size();
        bannerDefaultImage.setVisibility(imageUrlList.size() <= 0 ? VISIBLE : GONE);
        setIndicator();
        setViewPagerData();
    }

    /**
     * 必須調用
     */
    public void notifyDataChange(Object[] arr) {
        notifyDataChange(Arrays.asList(arr));
    }

    @SuppressLint("SetTextI18n")
    private void setIndicator() {
        lltIndicatorParent.removeAllViews();
        lltIndicatorParent.setVisibility(count > 1 ? View.VISIBLE : View.GONE);
        for (int i = 0; i < count; i++) {
            View indicatorView = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (L.dp_1 * 4), (int) (L.dp_1 * 4));
            params.leftMargin = (int) (L.dp_1 * 6);
            params.rightMargin = (int) (L.dp_1 * 6);
            indicatorView.setBackgroundColor(unselectedIndicatorColor);
            lltIndicatorParent.addView(indicatorView, params);
        }
    }

    private void setViewPagerData() {
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            viewPager.addOnPageChangeListener(this);
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(0);
        if (count > 1) { //跳到中間
            int centerPos = Integer.MAX_VALUE / 2;
            int center_0 = centerPos - (centerPos % count);
            viewPager.setCurrentItem(center_0, false);
        }
        onPageSelected(0);
        if (isAutoPlay) {
            startAutoPlay();
        }
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler.postDelayed(task, delayTime);
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (count == 0) return 0;
            return count == 1 ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
            return view == object;
        }

        List<ImageView> cacheImageViewList = new ArrayList<>();

        private ImageView getNoParentImageView() {
            int size = cacheImageViewList.size();
            for (int i = 0; i < size; i++) {
                ImageView imageView = cacheImageViewList.get(i);
                if (imageView != null && imageView.getParent() == null) {
                    return imageView;
                }
            }
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            cacheImageViewList.add(imageView);
            return imageView;
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, final int position) {
            int realPos = position % count;
            ImageView imageView = getNoParentImageView();
            if (imageLoader != null) {
                imageLoader.displayImage(context, imageUrlList.get(realPos), imageView);
            }
            container.addView(imageView);
            if (listener != null) {
                imageView.setOnClickListener(v -> listener.onClick(realPos));
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    private int lastSelectPosition = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        int curPos = position % count;
        lltIndicatorParent.getChildAt(lastSelectPosition).setBackgroundColor(unselectedIndicatorColor);
        lltIndicatorParent.getChildAt(curPos).setBackgroundColor(selectedIndicatorColor);
        lastSelectPosition = curPos;
    }

    public Banner setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }

    public interface OnBannerClickListener {
        void onClick(int position);
    }

    public static class BannerScroller extends Scroller {
        private int scrollTime = 800;

        public BannerScroller(Context context) {
            super(context);
        }

        public BannerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, scrollTime);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, scrollTime);
        }

        public void setScrollTime(int scrollTime) {
            this.scrollTime = scrollTime;
        }

    }

    public interface ImageLoader extends Serializable {
        void displayImage(Context context, Object path, ImageView imageView);
    }

}

