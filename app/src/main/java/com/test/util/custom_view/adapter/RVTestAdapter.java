package com.test.util.custom_view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class RVTestAdapter extends BaseAppRVAdapter {


    public RVTestAdapter(Context context, List<Map<String, String>> list) {
        super(context, R.layout.adapter_tv, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        String name = list.get(position).get("name");
        tv.setText(name);
    }

}
