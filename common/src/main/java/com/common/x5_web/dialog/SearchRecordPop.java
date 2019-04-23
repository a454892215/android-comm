package com.common.x5_web.dialog;

import android.support.v7.widget.RecyclerView;

import com.common.R;
import com.common.base.BaseActivity;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BasePop;
import com.common.helper.RVHelper;
import com.common.listener.OnClickListener;
import com.common.utils.CastUtil;
import com.common.utils.LogUtil;
import com.common.x5_web.adapter.SearchRecordAdapter;
import com.common.x5_web.entity.SearchRecordEntity;

import org.litepal.LitePal;
import org.litepal.crud.async.FindMultiExecutor;

import java.util.Collections;

public class SearchRecordPop extends BasePop {

    private OnClickListener onClickListener;
    private RecyclerView rv;

    public SearchRecordPop(BaseActivity activity) {
        super(activity);
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
    }

    @Override
    protected void updateView() {
        FindMultiExecutor<SearchRecordEntity> allAsync = LitePal.findAllAsync(SearchRecordEntity.class);
        allAsync.listen(entityList -> {
            LogUtil.d("=======SearchRecordEntity=======entityList:" + entityList.size());
            Collections.reverse(entityList);
            RVHelper.notifyAdapterRefresh(entityList, rv);
        });
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
