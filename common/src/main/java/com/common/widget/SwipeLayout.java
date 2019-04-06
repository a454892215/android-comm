package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.common.utils.DensityUtils;

public class SwipeLayout extends LinearLayout {
    private Context context;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View childView_0 = getChildAt(0);
        ViewGroup.LayoutParams lp = childView_0.getLayoutParams();
        lp.width = (int) DensityUtils.getWidth(context);
        childView_0.setLayoutParams(lp);
    }
}
