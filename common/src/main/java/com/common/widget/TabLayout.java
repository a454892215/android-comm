package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
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

public class TabLayout extends HorizontalScrollView {
    private Context context;
    private float text_size;
    private int selected_text_color;
    private int unselected_text_color;
    private int selected_bg_color;
    private int unselected_bg_color;
    private int lastClickPosition = 0;
    private int tb_height;
    private int tb_round_rect_radius;
    private LinearLayout llt_content;

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
        llt_content = new LinearLayout(context);
        llt_content.setOrientation(LinearLayout.HORIZONTAL);
        addView(llt_content);

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

    public void setData(String[] names) {
        llt_content.removeAllViews();
        lastClickPosition = 0;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            RelativeLayout rlt_item = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.group_item_tablayout, this, false);
            CommonTextView textView = rlt_item.findViewById(R.id.tv);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            lp.height = tb_height;
            textView.setLayoutParams(lp);
            textView.setText(name);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
            textView.setClipRadius(tb_round_rect_radius);
            updateTextViewState(textView, i == 0);
            int finalI = i;
            rlt_item.setOnClickListener(v -> {
                if (lastClickPosition != finalI) {
                    updateTextViewState(llt_content.getChildAt(lastClickPosition).findViewById(R.id.tv), false);
                    updateTextViewState(llt_content.getChildAt(finalI).findViewById(R.id.tv), true);
                    if (listener != null) listener.OnSelectChanged(finalI);
                    lastClickPosition = finalI;
                }
            });
            int itemWidth = Math.round((DensityUtils.getWidth(context) - getPaddingLeft() - getPaddingRight()) / names.length);
            itemWidth = tb_width == 0 ? itemWidth : tb_width;
            llt_content.addView(rlt_item, new LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    OnSelectChangedListener listener;

    private void updateTextViewState(TextView tv, boolean isSelect) {
        if (isSelect) {
            tv.setTextColor(selected_text_color);
            tv.setBackgroundColor(selected_bg_color);
        } else {
            tv.setTextColor(unselected_text_color);
            tv.setBackgroundColor(unselected_bg_color);
        }
    }

    public void setTabWidth(int tb_width) {
        this.tb_width = tb_width;
    }

    public void setUnselectedTextColor(int unselected_text_color) {
        this.unselected_text_color = unselected_text_color;
    }

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }

    public void setTextSize(float textSize) {
        this.text_size = textSize;
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int position);
    }
}
