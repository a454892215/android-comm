package com.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import com.common.R;
import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.ViewHolder;

import java.util.List;

/**
 * Author:  L
 * Description: No
 */

public class TextViewRVAdapter extends BaseRVAdapter<String> {
    public TextViewRVAdapter(Context activity, int itemLayoutId, List<String> list) {
        super(activity, itemLayoutId, list);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        View itemView = holder.itemView;
        TextView tv_1 = ViewHolder.get(itemView, R.id.tv_1);
        tv_1.setText(list.get(position));
    }
}
