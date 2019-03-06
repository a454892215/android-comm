package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class CommonTabLayout extends LinearLayout {

    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CommonTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View selectingItemView;

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        updateAllTabView();
    }

    private void updateAllTabView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);
            if (itemView.isSelected()) selectingItemView = itemView;
            int finalI = i;
            itemView.setOnClickListener(v -> {
                changeItemViewSelectedState(selectingItemView, false);
                changeItemViewSelectedState(itemView, true);
                selectingItemView = itemView;
                if (listener != null) listener.OnSelectChanged(finalI);
            });
        }
        if (selectingItemView == null && childCount > 0) {
            selectingItemView = getChildAt(0);
            changeItemViewSelectedState(selectingItemView, true);
        }
    }

    private void changeItemViewSelectedState(View itemView, boolean isSelected) {
        itemView.setSelected(isSelected);
        List<View> viewList = getAllChildViews(itemView);
        for (View view : viewList) {
            view.setSelected(isSelected);
        }
    }

    OnSelectChangedListener listener;

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int position);
    }

    public void setCurrentPosition(int position) {
        changeItemViewSelectedState(selectingItemView, false);
        selectingItemView = getChildAt(position);
        changeItemViewSelectedState(selectingItemView, true);
        if (listener != null) listener.OnSelectChanged(position);
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

}
