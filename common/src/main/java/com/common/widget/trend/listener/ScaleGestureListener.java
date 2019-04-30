package com.common.widget.trend.listener;

import android.view.ScaleGestureDetector;

import com.common.utils.LogUtil;

public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float currentSpanX = detector.getCurrentSpanX();
        LogUtil.d("=========onScale==========currentSpanX:" + currentSpanX);
        return currentSpanX > 0;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        float currentSpan = detector.getCurrentSpan();
        LogUtil.d("=========onScaleBegin==========currentSpan:" + currentSpan);
        return currentSpan > 0;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        super.onScaleEnd(detector);
    }
}
