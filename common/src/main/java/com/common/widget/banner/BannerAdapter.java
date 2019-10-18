package com.common.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.common.GlideApp;
import com.common.R;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.ViewHolder;

import java.util.List;

public class BannerAdapter extends BaseRVAdapter<String> {

    BannerAdapter(Context activity, List<String> list) {
        super(activity, R.layout.adapter_banner, list);
        isLoopMode = true;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        ImageView iv = ViewHolder.get(holder.itemView, R.id.iv);
        String url = list.get(position % list.size());
        GlideApp.with(context).load(url).into(iv);
    }
}
