package com.common.x5_web.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.WindowManager;

import com.common.R;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseDialogFragment;
import com.common.helper.RVHelper;
import com.common.listener.OnClickListener;
import com.common.utils.CastUtil;
import com.common.x5_web.adapter.SearchRecordAdapter;
import com.common.x5_web.entity.SearchRecordEntity;

public class SearchRecordDialogFragment extends BaseDialogFragment {

    private RecyclerView rv;

    private OnClickListener onClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_right);
        setDimeAmount(0f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setGravity(Gravity.TOP);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_web_search_record;
    }

    @Override
    protected void initView() {
        rv = findViewById(R.id.rv);
        BaseAppRVAdapter adapter = RVHelper.initVerticalRV(activity, null, rv, SearchRecordAdapter.class);
        adapter.setOnItemClick((view, position) -> {
            SearchRecordEntity entity = CastUtil.cast(adapter.getList().get(position));
            String url = entity.getUrl();
            if (onClickListener != null) {
                dismiss();
                onClickListener.onClick(url);
            }
        });
        updateUI();
    }

    public void updateUI() {
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
