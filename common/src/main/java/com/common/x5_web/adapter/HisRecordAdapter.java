package com.common.x5_web.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.common.R;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.widget.CommonTextView;

import java.util.List;
import java.util.Map;

public class HisRecordAdapter extends BaseAppRVAdapter {


    public HisRecordAdapter(Context context, List<Map<String, String>> list) {
        super(context, R.layout.adapter_tv_web_his_record, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        String url = list.get(position).get("url");
        tv.setText(url);
    }

}
