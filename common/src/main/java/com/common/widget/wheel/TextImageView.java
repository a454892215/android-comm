package com.common.widget.wheel;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Author:  Pan
 * CreateDate: 2019/10/22 10:13
 * Description: No
 */

public class TextImageView extends AppCompatImageView {

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
