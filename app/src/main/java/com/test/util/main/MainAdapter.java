package com.test.util.main;

import android.content.Context;
import androidx.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.CastUtil;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;
import java.util.Map;

public class MainAdapter extends BaseAppRVAdapter {


    public MainAdapter(Context context, List<Object> list) {
        super(context, R.layout.adapter_main_tv, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        Map<String,String> map = CastUtil.cast(list.get(position));
        String name = map.get("name");
        tv.setText(name);
    }

}
