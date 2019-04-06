package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.common.utils.DensityUtils;
import com.common.utils.LogUtil;

public class SwipeLayout extends LinearLayout {
    private Context context;
    //  private int rightViewWidth;

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
        View childView_0 = getChildAt(0);
        ViewGroup.LayoutParams lp = childView_0.getLayoutParams();
        lp.width = (int) DensityUtils.getWidth(context);
        childView_0.setLayoutParams(lp);
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View child_0 = getChildAt(1);
        if (child_0 != null) {
            //  rightViewWidth = child_0.getMeasuredWidth();
        }
    }

    private boolean isInit = true;

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (isInit) {
            isInit = false;
            ViewParent parent = getParent();
            if (parent instanceof MyHorizontalScrollView) {
                MyHorizontalScrollView hsv = (MyHorizontalScrollView) parent;
                hsv.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {
                    LogUtil.d("======onWindowVisibilityChanged======x:" + x + "  oldx:" + oldx);
                });
            }
        }
    }
}
