package com.common.widget.chart.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.common.R;
import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.widget.CommonTextView;
import com.common.widget.chart.RowCell;

import java.util.List;

/**
 * Author:  L
 * CreateDate: 2018/12/13 10:02
 * Description: No
 */

class TableRecyclerViewAdapter extends BaseRVAdapter<RowCell> {

    TableRecyclerViewAdapter(Context context, int itemLayoutId, List<RowCell> list) {
        super(context, itemLayoutId, list);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        LinearLayout llt_content = (LinearLayout) holder.itemView;
        RowCell rowCell = list.get(position);
        llt_content.setBackgroundColor(rowCell.rowBgColor);
        List<RowCell.TableCellEntity> cellList = rowCell.list;
        int size = cellList.size();
        int rowWidth = 0;
        for (int i = 0; i < size; i++) {
            CommonTextView tvCellView = (CommonTextView) llt_content.getChildAt(i);
            RowCell.TableCellEntity entity = cellList.get(i);
            if (tvCellView == null) {
                tvCellView = (CommonTextView) LayoutInflater.from(context).inflate(R.layout.table_item_llt_cell,llt_content,false);
                llt_content.addView(tvCellView, new LinearLayout.LayoutParams(entity.cellWidth, entity.cellHeight));
            }
            rowWidth += entity.cellWidth;
            tvCellView.setText(entity.span);
            tvCellView.setRightLineColor(entity.lineColor);
            tvCellView.setBottomLineColor(entity.lineColor);
            tvCellView.setTopLineColor(entity.lineColor);
            tvCellView.setLeftLineColor(entity.lineColor);

            tvCellView.setRightLineStrokeWidth(entity.lineStrokeSize);
            tvCellView.setBottomLineStrokeWidth(entity.lineStrokeSize);
            tvCellView.setTopLineStrokeWidth(entity.topLineStrokeSize);
            tvCellView.setLeftLineStrokeWidth(entity.leftLineStrokeSize);
        }
        ViewGroup.LayoutParams lp = llt_content.getLayoutParams();
        if (lp.width != rowWidth) {
            lp.width = rowWidth;
            llt_content.setLayoutParams(lp);
        }
    }
}
