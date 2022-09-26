package com.common.widget.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.common.R;
import com.common.comm.L;
import com.common.utils.LogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author:
 * 2020/11/15
 * Description: 基于ViewPager2
 */
public class Banner2 extends FrameLayout {
    private int delayTime = 2000;
    private boolean isAutoPlay = true;
    private static final int selectedIndicatorColor = Color.WHITE;
    private static final int unselectedIndicatorColor = Color.GRAY;
    private int count = 0;
    private final Context context;
    private ViewPager2 viewPager;
    private LinearLayout lltIndicatorParent;
    private ImageView bannerDefaultImage;
    private ImageLoader imageLoader;
    private Banner2Adapter adapter;
    private OnBannerClickListener listener;

    private final WeakHandler handler = new WeakHandler();

    public Banner2(Context context) {
        this(context, null);
    }

    public Banner2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        delayTime = 2000;
        View view = LayoutInflater.from(context).inflate(R.layout.banner2, this, false);
        bannerDefaultImage = view.findViewById(R.id.bannerDefaultImage);
        viewPager = view.findViewById(R.id.bannerViewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        lltIndicatorParent = view.findViewById(R.id.llt_indicator_parent);
        this.addView(view);
    }

    /**
     * 必須調用
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setPageTransformer(ViewPager2.PageTransformer transformer) {
        viewPager.setPageTransformer(transformer);
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
            adapter = new Banner2Adapter();
            viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(0);
        if (count > 1) { //跳到中間
            int centerPos = Integer.MAX_VALUE / 2;
            int center_0 = centerPos - (centerPos % count);
            viewPager.setCurrentItem(center_0, false);
        }
        onPageChangeCallback.onPageSelected(0);
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
        private static final int during = 800;

        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                View view0 = viewPager.getChildAt(0);
                if (view0 instanceof RecyclerView) {
                    ((RecyclerView) view0).smoothScrollBy(view0.getWidth(), 0, new AccelerateDecelerateInterpolator(), during);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
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


    private class Banner2Adapter extends RecyclerView.Adapter<BaseViewHolder> {


        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            int match_p = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(new ViewGroup.LayoutParams(match_p, match_p));
            return new BaseViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            int realPos = position % count;
            ImageView imageView = holder.itemView;
            if (imageLoader != null) {
                imageLoader.displayImage(context, imageUrlList.get(realPos), imageView);
            }
            if (listener != null) {
                imageView.setOnClickListener(v -> listener.onClick(realPos));
            }
        }

        @Override
        public int getItemCount() {
            if (count == 0) return 0;
            return count == 1 ? 1 : Integer.MAX_VALUE;
        }


    }

    private int lastSelectPosition = 0;


    public Banner2 setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }

    public interface OnBannerClickListener {
        void onClick(int position);
    }


    ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int curPos = position % count;
            lltIndicatorParent.getChildAt(lastSelectPosition).setBackgroundColor(unselectedIndicatorColor);
            lltIndicatorParent.getChildAt(curPos).setBackgroundColor(selectedIndicatorColor);
            lastSelectPosition = curPos;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    public interface ImageLoader extends Serializable {
        void displayImage(Context context, Object path, ImageView imageView);
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemView;

        public BaseViewHolder(ImageView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

}

