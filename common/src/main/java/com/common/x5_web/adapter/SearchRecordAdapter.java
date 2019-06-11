package com.common.x5_web.adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import com.common.R;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.CastUtil;
import com.common.utils.ViewHolder;
import com.common.widget.CommonTextView;
import com.common.x5_web.entity.SearchRecordEntity;

import java.util.List;

public class SearchRecordAdapter extends BaseAppRVAdapter {


    public SearchRecordAdapter(Context context, List<Object> list) {
        super(context, R.layout.adapter_tv_web_search_record, list);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        CommonTextView tv_title = holder.itemView.findViewById(R.id.tv_title);
        CommonTextView tv_url = holder.itemView.findViewById(R.id.tv_url);
        SearchRecordEntity entity = CastUtil.cast(list.get(position));
        tv_title.setText(entity.getTitle());
        tv_url.setText(entity.getUrl());
        ViewHolder.get(holder.itemView, R.id.tv_action_1).setOnClickListener(v -> {
            holder.itemView.scrollTo(0, 0);
            holder.itemView.postDelayed(() -> {
                SearchRecordEntity entity_del = CastUtil.cast(list.get(position));
                entity_del.delete();
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, list.size());
            }, 110);

        });
    }

}
