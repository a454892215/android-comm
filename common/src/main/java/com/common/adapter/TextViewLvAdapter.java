package com.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.R;
import com.common.base.BaseActivity;
import com.common.base.BaseLVAdapter;
import com.common.utils.ViewHolder;

import java.util.List;

public class TextViewLvAdapter extends BaseLVAdapter<String> {
    public TextViewLvAdapter(BaseActivity activity, List<String> list, int layoutId) {
        super(activity, list, layoutId);
    }

    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup) {
        itemView =  super.getView(position, itemView, viewGroup);
        TextView view = ViewHolder.get(itemView, R.id.tv_1);
        view.setText(list.get(position));
        return itemView;
    }
}
