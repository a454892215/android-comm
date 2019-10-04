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

public class RVTestHorizontalAdapter extends BaseAppRVAdapter {


    public RVTestHorizontalAdapter(Context context, List<Object> list) {
        super(context, R.layout.adapter_tv_3, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        tv.setBackgroundColor(position % 2 == 0 ? 0xffff0000 : 0xffffff00);
        Map<String, String> map = CastUtil.cast(list.get(position));
        String name = map.get("name");
        tv.setText(name);
    }

}
