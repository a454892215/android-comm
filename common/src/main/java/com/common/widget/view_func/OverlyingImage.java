package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;

import com.common.R;

/**
 * Author:  Pan
 * CreateDate: 2019/7/23 15:06
 * Description: No
 */

public class OverlyingImage {
    private View view;
    private final Rect rect;
    private float width, left, top, stepHeight;
    private int imageCount = 0;

    public OverlyingImage(View view, float left, float top) {
        width = view.getResources().getDimension(R.dimen.dp_22);
        stepHeight = view.getResources().getDimension(R.dimen.dp_2);
        this.left = left;
        this.top = top;
        this.view = view;
        rect = new Rect();
    }

    //存储相应位置drawable 的index
    private SparseIntArray drawableIndexArray = new SparseIntArray();

    Drawable[] drawableCmArr = new Drawable[10];//TODO

    public void onDraw(Canvas canvas) {
        //默认筹码绘制在View中心
        for (int i = 0; i < imageCount; i++) {
            rect.left = rightEnable ? Math.round(view.getWidth() - width + left) : Math.round(view.getWidth() / 2f - width / 2f + left);
            rect.right = Math.round(rect.left + width);
            float height = getPicHeightByWidth(drawableCmArr[drawableIndexArray.get(i)], width);
            rect.top = Math.round(view.getHeight() / 2f - height / 2f + top - i * stepHeight);
            rect.bottom = Math.round(rect.top + height / 1.8f);
            drawableCmArr[drawableIndexArray.get(i)].setBounds(rect);
            drawableCmArr[drawableIndexArray.get(i)].draw(canvas);
        }

    }

    private float getPicHeightByWidth(Drawable drawable, float width) {
        return width * drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth();
    }

    private boolean rightEnable;

    public void setRightEnable(boolean rightEnable) {
        this.rightEnable = rightEnable;
    }


    public void addImage(int drawableIndex) {
        drawableIndexArray.append(imageCount, drawableIndex);
        imageCount++;
        view.invalidate();
    }

    public void removeImage() {
        imageCount--;
        drawableIndexArray.removeAt(imageCount);
        view.invalidate();
    }

    public void clearImage() {
        drawableIndexArray.clear();
        imageCount = 0;
        view.invalidate();
    }
}
