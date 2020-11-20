package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.common.R;
import com.common.listener.OnSelectChangedListener;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class CommonTabLayout extends LinearLayout {

    private Context context;

    private int currentPosition = 0;

    private boolean repeatClickEnable = false; //是否可重复点击

    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTabLayout, defStyleAttr, 0);
        float clipRadius = typedArray.getDimension(R.styleable.CommonTabLayout_radius, 0);
        typedArray.recycle();

        if (clipRadius > 0) {
            this.setClipToOutline(true);
            this.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                    outline.setRoundRect(rect, clipRadius);
                }
            });
        }
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        updateAllTabView();
    }

    boolean isBindViewPager = false;

    private void updateAllTabView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);
            changeItemViewSelectedState(itemView, currentPosition == i);
            int indexInParent = i;
            itemView.setOnClickListener(v -> {
                if (fastTime > 0 && FastClickUtil.isFastClick(fastTime)) {
                    return;
                }
                if (!repeatClickEnable && currentPosition == indexInParent) return;
                changeItemViewSelectedState(getChildAt(currentPosition), false);
                changeItemViewSelectedState(v, true);
                currentPosition = indexInParent;
                if (listener != null) listener.OnSelectChanged(indexInParent);
                if (indicatorView != null && !isBindViewPager) {
                    indicatorView.animate().setInterpolator(new DecelerateInterpolator()).x(indicatorWidth * indexInParent).setDuration(250).start();
                }
            });
        }
    }

    private void changeItemViewSelectedState(View itemView, boolean isSelected) {
        if (itemView == null) return;
        itemView.setSelected(isSelected);
        List<View> viewList = getAllChildViews(itemView);
        for (View view : viewList) {
            view.setSelected(isSelected);
        }
    }


    public void setCurrentPosition(int position) {
        currentPosition = position;
        updateAllTabView();
        if (listener != null) listener.OnSelectChanged(currentPosition);
        if (indicatorView != null && !isBindViewPager) {
            indicatorView.animate().setInterpolator(new DecelerateInterpolator()).x(indicatorWidth * position).setDuration(250).start();
        }
    }

    public void setData(Object[] names, int layoutId, int tvNameId) {
        for (Object name : names) {
            View item = LayoutInflater.from(context).inflate(layoutId, this, false);
            TextView tv = item.findViewById(tvNameId);
            tv.setText(name.toString());
            addView(item);
        }
    }

    private List<View> getAllChildViews(View view) {
        List<View> list = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View v = vp.getChildAt(i);
                list.add(v);
                list.addAll(getAllChildViews(v));
            }
        }
        return list;
    }

    private OnSelectChangedListener listener;
    private View indicatorView;
    private int indicatorWidth;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(() -> {
            View childView = getChildAt(0);
            if (childView != null && indicatorViewId != 0) {
                View parentView = (View) getParent();//设置水平indicator
                indicatorView = parentView.findViewById(indicatorViewId);
                if (indicatorView != null) {
                    indicatorWidth = childView.getMeasuredWidth();
                    ViewGroup.LayoutParams lp = indicatorView.getLayoutParams();
                    lp.width = indicatorWidth;
                    indicatorView.setLayoutParams(lp);
                } else {
                    LogUtil.e("=======flt_tab_indicator 为 null================");
                }
            }
        });

    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    private int indicatorViewId;

    public void setIndicatorViewId(int indicatorViewId) {
        this.indicatorViewId = indicatorViewId;
    }


    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    private int fastTime = 0;

    public void setFastClickTime(int time) {
        fastTime = time;
    }


    public void setRepeatClickEnable(boolean repeatClickEnable) {
        this.repeatClickEnable = repeatClickEnable;
    }

    public void bindViewPagerAndIndicator(ViewPager viewPager) {
        isBindViewPager = true;
        setOnSelectChangedListener(viewPager::setCurrentItem);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float progress = position + positionOffset;
                if (indicatorView != null) {
                    indicatorView.setTranslationX(progress * indicatorWidth);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
