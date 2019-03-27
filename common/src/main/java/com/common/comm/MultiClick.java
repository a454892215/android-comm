package com.common.comm;

import android.os.SystemClock;

/**
 * Author:  L
 * CreateDate: 2019/1/22 14:58
 * Description: No
 */

public class MultiClick {
    private static final int COUNTS = 5;//点击次数
    private static final long DURATION = 700;//规定有效时间
    private long[] mHits = new long[COUNTS];

    public boolean isToFastClickCondition() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        return (SystemClock.uptimeMillis() - mHits[0]) < DURATION;
    }
}
