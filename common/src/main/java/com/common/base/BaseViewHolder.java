package com.common.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: L
 * Description:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
}
