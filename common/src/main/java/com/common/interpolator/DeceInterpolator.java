package com.common.interpolator;

import android.view.animation.Interpolator;

import com.common.utils.LogUtil;
@Deprecated
public class DeceInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        LogUtil.d("input:" + input);
        return input < 0.6 ? input : input;
    }
}
