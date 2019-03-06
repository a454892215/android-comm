package com.common.base;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: Pan
 * Description:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
}
