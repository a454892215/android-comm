package com.test.util.comm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.utils.ViewHolder;
import com.test.util.R;
import com.test.util.base.BaseLvAdapter;

import java.util.List;

public class SimpleLvTextAdapter extends BaseLvAdapter<String> {


    public SimpleLvTextAdapter(List<String> list) {
        super(list, R.layout.adapter_tv_2);
    }

    @Override
    public void onNeedUpdateItem(int position, View convertView, ViewGroup parent) {
        TextView tv = ViewHolder.get(convertView, R.id.tv_1);
        tv.setText(list.get(position));
    }


}
