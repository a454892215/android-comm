package com.common.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.utils.CastUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  L
 * Description: No
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected static final int VIEW_TYPE_EMPTY = 1;
    private static final int VIEW_TYPE_1 = 0;

    protected Context context;
    protected List<T> list = new ArrayList<>();
    private int layout_id_type_1;
    private int emptyLayoutId = R.layout.adater_empty_view;
    protected final float dp_1;

    public BaseRVAdapter(Context context, int itemLayoutId, List<T> list) {
        this.context = context;
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        if (list != null) this.list.addAll(list);
        this.layout_id_type_1 = itemLayoutId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_EMPTY:
                view = LayoutInflater.from(context).inflate(emptyLayoutId, parent, false);
                break;
            case VIEW_TYPE_1:
                view = LayoutInflater.from(context).inflate(layout_id_type_1, parent, false);
                break;
        }
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        if (onItemClick != null) {
            OnClick onClick = CastUtil.cast(holder.itemView.getTag(R.id.key_item_click));
            if (onClick == null) {
                onClick = new OnClick();
                holder.itemView.setTag(R.id.key_item_click, onClick);
            }
            onClick.setView(holder.itemView);
            onClick.setPosition(position);
            holder.itemView.setOnClickListener(onClick);
        }

    }

    @Override
    public int getItemCount() {
        int size = list.size();
        size = size == 0 ? 1 : size;
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        int size = list.size();
        return size == 0 ? VIEW_TYPE_EMPTY : VIEW_TYPE_1;
    }

    protected void setEmptyLayoutId(int emptyLayoutId) {
        this.emptyLayoutId = emptyLayoutId;
    }

    public List<T> getList() {
        return list;
    }

    private class OnClick implements View.OnClickListener {
        private View view;
        private int position;

        private void setView(View view) {
            this.view = view;
        }

        private void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(view, position);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }
}
