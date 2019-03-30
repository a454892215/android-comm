package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        updateAllTabView();
    }

    private void updateAllTabView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);
            changeItemViewSelectedState(itemView, currentPosition == i);
            int indexInParent = i;
            itemView.setOnClickListener(v -> {
                if (currentPosition == indexInParent) return;
                changeItemViewSelectedState(getChildAt(currentPosition), false);
                changeItemViewSelectedState(v, true);
                currentPosition = indexInParent;
                if (listener != null) listener.OnSelectChanged(indexInParent);
                if (indicatorView != null) {
                    indicatorView.animate().setInterpolator(new DecelerateInterpolator()).x(indicatorWidth * indexInParent).setDuration(250).start();
                }
            });
        }
    }

    private void changeItemViewSelectedState(View itemView, boolean isSelected) {
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
        if (indicatorView != null) {
            indicatorView.animate().setInterpolator(new DecelerateInterpolator()).x(indicatorWidth * position).setDuration(250).start();
        }
    }

    public void setData(String[] names, int layoutId, int tvNameId) {
        for (String name : names) {
            View item = LayoutInflater.from(context).inflate(layoutId, this, false);
            TextView tv = item.findViewById(tvNameId);
            tv.setText(name);
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

    OnSelectChangedListener listener;
    View indicatorView;
    int indicatorWidth;

    public void setIndicatorView(View indicatorView, int indicatorWidth) {
        this.indicatorView = indicatorView;
        this.indicatorWidth = indicatorWidth;
    }

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int position);
    }

}
