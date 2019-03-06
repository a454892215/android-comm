package com.common.helper;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: ${VenRen}
 * created on: 2019/2/28 10:19
 * description: RecyclerView 边距
 */
public class ItemMarginHelper extends RecyclerView.ItemDecoration {

    private int space;

    public ItemMarginHelper(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }

}
