package com.test.util.custom_view.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.ViewHolder;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class SwipeTestAdapter extends BaseAppRVAdapter {

    public SwipeTestAdapter(Context activity, List<Map<String, String>> list) {
        super(activity, R.layout.adapter_swipte_test, list);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = ViewHolder.get(holder.itemView, R.id.tv_1);
        ViewHolder.get(holder.itemView, R.id.tv_action_1).setOnClickListener(v -> {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size() - position);
        });
        String name = list.get(position).get("name");
        tv.setText(name);
    }
}
