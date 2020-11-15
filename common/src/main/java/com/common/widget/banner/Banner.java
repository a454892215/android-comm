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
import java.util.List;

/**
 * Author:
 * 2020/11/15
 * Description: Banner
 */
@SuppressWarnings("unused")
public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int delayTime = 2000;
    private boolean isAutoPlay = true;
    private int selectedIndicatorColor = Color.GRAY;
    private int unselectedIndicatorColor = Color.WHITE;
    private int mLayoutResId = R.layout.banner;
    private int count = 0;
    private int currentItem;
    private int lastPosition = 1;
    private ScaleType scaleType = ScaleType.CENTER_CROP;
    private List<String> imageUrls;
    private List<View> imageViewList;
    private Context context;
    private BannerViewPager viewPager;
    private LinearLayout lltIndicatorParent;
    private ImageView bannerDefaultImage;
    private ImageLoader imageLoader;
    private BannerPagerAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private OnBannerListener listener;

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
        imageUrls = new ArrayList<>();

        initView(context, attrs);
        initViewPagerScroll();
    }

    private void initView(Context context, AttributeSet attrs) {
        imageViewList = new ArrayList<>();
        mIndicatorWidth = (int) (L.dp_1 * 4);
        mIndicatorHeight = (int) (L.dp_1 * 4);
        mIndicatorMargin = (int) (L.dp_1 * 6);
        scaleType = ScaleType.CENTER_CROP;
        delayTime = 2000;
        isAutoPlay = true;
        View view = LayoutInflater.from(context).inflate(mLayoutResId, this, true);
        bannerDefaultImage = (ImageView) view.findViewById(R.id.bannerDefaultImage);
        viewPager = (BannerViewPager) view.findViewById(R.id.bannerViewPager);
        lltIndicatorParent = (LinearLayout) view.findViewById(R.id.llt_indicator_parent);
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

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }


    public Banner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            LogUtil.e("Please set the PageTransformer class");
        }
        return this;
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.count = imageUrls.size();
    }

    public void update(List<String> imageUrls) {
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        this.imageViewList.clear();
        this.count = this.imageUrls.size();
        start();
    }

    public void start() {
        setImageList(imageUrls);
        setIndicator();
        setViewPagerData();
    }

    @SuppressLint("SetTextI18n")
    private void setImageList(List<String> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            bannerDefaultImage.setVisibility(VISIBLE);
            LogUtil.e("The Banner data set is empty.");
            return;
        }
        bannerDefaultImage.setVisibility(GONE);
        imageViewList.clear();
        for (int i = 0; i <= count + 1; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(scaleType);
            String url;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViewList.add(imageView);
            if (imageLoader != null) {
                imageLoader.displayImage(context, url, imageView);
            } else {
                LogUtil.e("Please set images loader.");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setIndicator() {
        lltIndicatorParent.removeAllViews();
        lltIndicatorParent.setVisibility(count > 1 ? View.VISIBLE : View.GONE);
        for (int i = 0; i < count; i++) {
            View indicatorView = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            indicatorView.setBackgroundColor(i == 0 ? selectedIndicatorColor : unselectedIndicatorColor);
            lltIndicatorParent.addView(indicatorView, params);
        }
    }

    private void setViewPagerData() {
        currentItem = 1;
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            viewPager.addOnPageChangeListener(this);
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        viewPager.setScrollable(count > 1);
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
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
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

    /**
     * @return 从0开始
     */
    public int getRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
            return view == object;
        }

        @NotNull
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViewList.get(position));
            View view = imageViewList.get(position);
            if (listener != null) {
                view.setOnClickListener(v -> listener.OnBannerClick(getRealPosition(position)));
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(getRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(getRealPosition(position));
        }
        lltIndicatorParent.getChildAt((lastPosition - 1 + count) % count).setBackgroundColor(unselectedIndicatorColor);
        lltIndicatorParent.getChildAt((position - 1 + count) % count).setBackgroundColor(selectedIndicatorColor);
        lastPosition = position;
    }

    public Banner setOnBannerListener(OnBannerListener listener) {
        this.listener = listener;
        return this;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }

    public interface OnBannerListener {
        void OnBannerClick(int position);
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
        void displayImage(Context context, String path, ImageView imageView);
    }

}

