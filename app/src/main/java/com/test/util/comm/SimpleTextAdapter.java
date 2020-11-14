package com.test.util.comm;

import android.content.Context;

import androidx.annotation.NonNull;

import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.widget.CommonTextView;
import com.test.util.R;

import java.util.List;

public class SimpleTextAdapter extends BaseRVAdapter<String> {


    public SimpleTextAdapter(Context context, List<String> list) {
        super(context, R.layout.adapter_tv_2, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv = (CommonTextView) holder.itemView;
        tv.setText(list.get(position));
    }

}
