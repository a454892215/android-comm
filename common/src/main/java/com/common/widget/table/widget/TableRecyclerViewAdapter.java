package com.common.widget.table.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.widget.CommonTextView;
import com.common.widget.table.RowCell;

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
                tvCellView = new CommonTextView(context);
                llt_content.addView(tvCellView, new LinearLayout.LayoutParams(entity.cellWidth, entity.cellHeight));
            }
            rowWidth += entity.cellWidth;
            tvCellView.setText(entity.span);

            tvCellView.setRightLineColor(entity.lineColor);
            tvCellView.setBottomLineColor(entity.lineColor);
            tvCellView.setRightLineStrokeWidth(dp_1);
            tvCellView.setBottomLineStrokeWidth(dp_1);

        }
        ViewGroup.LayoutParams lp = llt_content.getLayoutParams();
        if (lp.width != rowWidth) {
            lp.width = rowWidth;
            llt_content.setLayoutParams(lp);
        }
    }
}
