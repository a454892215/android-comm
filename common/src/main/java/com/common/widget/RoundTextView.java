package com.common.widget;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.common.R;

public class RoundTextView extends android.support.v7.widget.AppCompatTextView {
    private float radius = 0;

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = context.getResources().getDimension(R.dimen.dp_5);
        this.setClipToOutline(true);
        this.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                outline.setRoundRect(rect, radius);
            }
        });
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
