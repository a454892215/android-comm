package com.common.widget.table.widget;

import android.content.Context;

import com.common.base.BaseRVAdapter;
import com.common.widget.table.RowCell;

import java.util.List;

/**
 * Author:  L
 * CreateDate: 2018/12/13 10:02
 * Description: No
 */

class TableRecyclerViewAdapter extends BaseRVAdapter<RowCell> {

    TableRecyclerViewAdapter(Context activity, int itemLayoutId, List<RowCell> list) {
        super(activity, itemLayoutId, list);
    }
}
