package com.common.widget.chart.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.common.R;
import com.common.base.BaseRVAdapter;
import com.common.base.BaseViewHolder;
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
            CellView tvCellView = (CellView) llt_content.getChildAt(i);
            RowCell.TableCellEntity entity = cellList.get(i);
            if (tvCellView == null) {
                tvCellView = (CellView) LayoutInflater.from(context).inflate(R.layout.table_item_llt_cell, llt_content, false);
                llt_content.addView(tvCellView, new LinearLayout.LayoutParams(entity.cellWidth, entity.cellHeight));
            }
            rowWidth += entity.cellWidth;
            tvCellView.setTextColor(entity.textColor);
            tvCellView.setTextColors(entity.textColors);
            tvCellView.setTextArr(entity.textArr);
            tvCellView.setBgColorArr(entity.bgColorArr);
            tvCellView.setHorizontalSpacing(entity.horizontalSpacing);

            tvCellView.setRectSize(entity.rectSize);
            tvCellView.setRectHeight(entity.rectHeight);
            tvCellView.setRectBgRadius(entity.rectBgRadius);

            if (entity.strokeInfo != null) {
                tvCellView.setStrokeBg(entity.strokeInfo.isStroke, entity.strokeInfo.strokeColor, entity.strokeInfo.strokeWidth);
            }

            tvCellView.getLineShape().setLineColor(entity.lineColor);
            tvCellView.getLineShape().setRightLineStrokeWidth(entity.rightLineStrokeSize);
            tvCellView.getLineShape().setBottomLineStrokeWidth(entity.bottomLineStrokeSize);
            tvCellView.getLineShape().setTopLineStrokeWidth(entity.topLineStrokeSize);
            tvCellView.getLineShape().setLeftLineStrokeWidth(entity.leftLineStrokeSize);

            if (entity.bgTextShape != null) entity.bgTextShape.setView(tvCellView);
            tvCellView.setBgShape(entity.bgTextShape);
        }
        ViewGroup.LayoutParams lp = llt_content.getLayoutParams();
        if (lp.width != rowWidth) {
            lp.width = rowWidth;
            llt_content.setLayoutParams(lp);
        }
    }
}
