package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;
import com.common.utils.DensityUtils;

/**
 * Author:  L
 * CreateDate: 2019/1/7 14:29
 * Description: No
 */

public class TabLayout extends LinearLayout {
    private Context context;
    private float text_size;
    private int selected_text_color;
    private int unselected_text_color;
    private int selected_bg_color;
    private int unselected_bg_color;
    private int currentPosition = 0;
    private int tb_height;
    private int tb_round_rect_radius;

    private int tb_width;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabLayout, defStyleAttr, 0);
        text_size = typedArray.getDimension(R.styleable.TabLayout_tb_text_size, 14);
        tb_height = Math.round(typedArray.getDimension(R.styleable.TabLayout_tb_height, 10));
        tb_width = Math.round(typedArray.getDimension(R.styleable.TabLayout_tb_width, 0));
        tb_round_rect_radius = Math.round(typedArray.getDimension(R.styleable.TabLayout_tb_round_rect_radius, 10));
        selected_text_color = typedArray.getColor(R.styleable.TabLayout_tb_selected_text_color, Color.WHITE);
        unselected_text_color = typedArray.getColor(R.styleable.TabLayout_tb_unselected_text_color, Color.BLACK);
        selected_bg_color = typedArray.getColor(R.styleable.TabLayout_tb_selected_bg_color, Color.BLUE);
        unselected_bg_color = typedArray.getColor(R.styleable.TabLayout_tb_unselected_bg_color, Color.WHITE);
        typedArray.recycle();
    }


    @SuppressWarnings("unused")
    public void setCurrentPosition(int position) {
        currentPosition = position;
    }

    public void setData(String[] names) {
        removeAllViews();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            RelativeLayout rlt_item = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.template_hor_scroll_tab_item_1, this, false);
            CommonTextView textView = rlt_item.findViewById(R.id.tv);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            lp.height = tb_height;
            textView.setLayoutParams(lp);
            textView.setText(name);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
            textView.setClipRadius(tb_round_rect_radius);
            updateTextViewState(textView, i == currentPosition);
            int finalI = i;
            rlt_item.setOnClickListener(v -> {
                if (currentPosition != finalI) {
                    updateTextViewState(getChildAt(currentPosition).findViewById(R.id.tv), false);
                    updateTextViewState(getChildAt(finalI).findViewById(R.id.tv), true);
                    if (listener != null) listener.OnSelectChanged(finalI);
                    currentPosition = finalI;
                }
            });
            int itemWidth = Math.round((DensityUtils.getWidth(context) - getPaddingLeft() - getPaddingRight()) / names.length);
            itemWidth = tb_width == 0 ? itemWidth : tb_width;
            addView(rlt_item, new LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            if (currentPosition > -1) {
                if (listener != null) listener.OnSelectChanged(currentPosition);
            }
        }
    }


    private void updateTextViewState(TextView tv, boolean isSelect) {
        if (isSelect) {
            tv.setTextColor(selected_text_color);
            tv.setBackgroundColor(selected_bg_color);
        } else {
            tv.setTextColor(unselected_text_color);
            tv.setBackgroundColor(unselected_bg_color);
        }
    }

    OnSelectChangedListener listener;

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int position);
    }
}
