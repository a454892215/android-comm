package com.test.util.custom_view.rv.adapter;

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

public class SnapTestAdapter extends BaseAppRVAdapter {

    public SnapTestAdapter(Context activity, List<Object> list) {
        super(activity, R.layout.adapter_snap_test, list);
    }

    private int[] colors = {R.color.light_purple, R.color.sky_blue};

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = ViewHolder.get(holder.itemView, R.id.tv_1);
        Map<String, String> map = CastUtil.cast(list.get(position));
        String name = map.get("name");
        tv.setText(name);
        holder.itemView.setBackgroundColor(context.getResources().getColor(colors[position % colors.length]));
    }
}
