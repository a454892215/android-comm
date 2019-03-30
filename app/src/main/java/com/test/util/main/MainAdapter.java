package com.test.util.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.LogUtil;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class MainAdapter extends BaseAppRVAdapter {


    public MainAdapter(Context context, List<Map<String, String>> list) {
        super(context, R.layout.adapter_main_tv, list);
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
