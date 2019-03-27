package com.common.utils;

import android.util.SparseArray;
import android.view.View;

import com.common.R;

/**
 * Author:  L
 * Description: No
 */

public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> sparseArray = (SparseArray<View>) view.getTag(R.id.view_holder);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            view.setTag(R.id.view_holder,sparseArray);
        }
        View childView = sparseArray.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            sparseArray.put(id, childView);
        }
        return (T) childView;
    }
}
