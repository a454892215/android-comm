package com.test.util.custom_view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.CastUtil;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class RVTestAdapter extends BaseAppRVAdapter {


    public RVTestAdapter(Context context, List<Object> list) {
        super(context, R.layout.adapter_tv_2, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        Map<String, String> map = CastUtil.cast(list.get(position));
        String name = map.get("name");
        tv.setText(name);
    }

}
