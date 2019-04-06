package com.test.util.custom_view.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
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
    }
}
