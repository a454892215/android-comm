package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FlowLayout extends FrameLayout {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
