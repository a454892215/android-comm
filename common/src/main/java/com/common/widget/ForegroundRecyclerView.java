package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class ForegroundRecyclerView extends HRecyclerView {

    public ForegroundRecyclerView(Context context) {
        this(context, null);
    }

    public ForegroundRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public ForegroundRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}