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
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        LogUtil.d("=====overScrollBy=====deltaY：" + deltaY + "  scrollY:" + scrollY + " scrollRangeY:"
                + scrollRangeY + " maxOverScrollY:" + maxOverScrollY + "  isTouchEvent:" + isTouchEvent);

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

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
