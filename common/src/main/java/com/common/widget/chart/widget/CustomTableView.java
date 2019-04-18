package com.common.widget.chart.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;
import com.common.widget.chart.CoordinateEntity;
import com.common.widget.chart.RowCell;

import java.util.List;

/**
 * Author:  L
 * CreateDate: 2018/12/13
 * Description: No
 */
public class CustomTableView extends FrameLayout {
    private Context context;
    private RecyclerView table_rv_header_left;
    public RecyclerView table_rv_body_left;

    private RecyclerView table_rv_header_right;
    public RecyclerView table_rv_body_right;

    private LineView custom_line_view;

    public CustomTableView(@NonNull Context context) {
        this(context, null);
    }

    public CustomTableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_custom_table, this, true);
        custom_line_view = findViewById(R.id.custom_line_view);

        AsyncScrollLayout async_scroll_layout = findViewById(R.id.async_scroll_layout);

        table_rv_header_left = inflate.findViewById(R.id.table_rv_header_left);
        table_rv_header_left.setLayoutManager(new LinearLayoutManager(context));

        table_rv_body_left = inflate.findViewById(R.id.table_rv_body_left);
        table_rv_body_left.setLayoutManager(new LinearLayoutManager(context));

        table_rv_header_right = inflate.findViewById(R.id.table_rv_header_right);
        table_rv_header_right.setLayoutManager(new HLayoutManager(context));

        table_rv_body_right = inflate.findViewById(R.id.table_rv_body_right);
        table_rv_body_right.setLayoutManager(new HLayoutManager(context));

        async_scroll_layout.addRecyclerViewGroup(table_rv_body_left, table_rv_body_right, table_rv_header_right);
        async_scroll_layout.setAsyncScrollView(custom_line_view);
    }

    public void setHeaderData(List<RowCell> leftHeaderList, List<RowCell> rightHeaderList) {
        if (leftHeaderList != null && leftHeaderList.size() > 0) {
            setAdapter(leftHeaderList, table_rv_header_left);
        }

        if (rightHeaderList != null && rightHeaderList.size() > 0) {
            setAdapter(rightHeaderList, table_rv_header_right);
        }
    }

    public void setBodyData(List<RowCell> leftColumnList, List<RowCell> rightColumnList) {
        if (leftColumnList != null && leftColumnList.size() > 0) {
            setAdapter(leftColumnList, table_rv_body_left);
        }
        if (rightColumnList != null && rightColumnList.size() > 0) {
            table_rv_body_left.setVerticalScrollBarEnabled(false);
            setAdapter(rightColumnList, table_rv_body_right);
        }
    }


    private void setAdapter(List<RowCell> data, RecyclerView rv) {
        TableRecyclerViewAdapter adapter = (TableRecyclerViewAdapter) rv.getAdapter();
        if (adapter == null) {
            adapter = new TableRecyclerViewAdapter(context, R.layout.table_item_llt_line, data);
            rv.setAdapter(adapter);
        } else {
            adapter.getList().clear();
            adapter.getList().addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    public LineView getLineView() {
        return custom_line_view;
    }

    public void addLineData(List<CoordinateEntity> coordinateList) {
        custom_line_view.setVisibility(View.VISIBLE);
        custom_line_view.addData(coordinateList);
    }

    public void setLineColor(int color) {
        custom_line_view.setLineColor(color);
    }

    public void setLineRadius(float lineRadius) {
        custom_line_view.setLineRadius(lineRadius);
    }

}
