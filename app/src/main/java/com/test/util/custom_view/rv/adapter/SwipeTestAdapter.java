package com.test.util.custom_view.rv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.CastUtil;
import com.common.utils.ViewHolder;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class SwipeTestAdapter extends BaseAppRVAdapter {

    public SwipeTestAdapter(Context activity, List<Object> list) {
        super(activity, R.layout.adapter_swipte_test, list);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = ViewHolder.get(holder.itemView, R.id.tv_1);
        ViewHolder.get(holder.itemView, R.id.tv_action_1).setOnClickListener(v -> {
            holder.itemView.scrollTo(0, 0);
            holder.itemView.postDelayed(() -> {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, list.size());
            },120);

        });
        Map<String,String> map = CastUtil.cast(list.get(position));
        String name =map.get("name");
        tv.setText(name);
    }
}
