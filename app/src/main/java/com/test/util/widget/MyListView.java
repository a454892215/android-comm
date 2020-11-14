package com.test.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.common.utils.LogUtil;

/**
 * Author: Pan
 * 2020/11/14
 * Description:
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float distance = t - oldt;
        LogUtil.d("l:" + l + "  t:" + t + "  oldl:" + oldl + "  oldt:" + oldt + "  distance:" + distance);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
        LogUtil.d("=====scrollBy=====");
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        LogUtil.d("=====scrollTo=====");
    }
}
