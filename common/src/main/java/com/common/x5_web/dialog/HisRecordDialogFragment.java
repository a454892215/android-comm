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
import com.common.x5_web.adapter.HisRecordAdapter;
import com.common.x5_web.entity.HistoryRecordEntity;

import org.litepal.LitePal;
import org.litepal.crud.async.FindMultiExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HisRecordDialogFragment extends BaseDialogFragment {

    private RecyclerView rv;

    private OnClickHistoryUrl onClickHistoryUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_bottom).setGravity(Gravity.BOTTOM);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_web_his_record;
    }

    @Override
    protected void initView() {
        rv = findViewById(R.id.rv);
        BaseAppRVAdapter adapter = RVHelper.initVerticalRV(activity, null, rv, HisRecordAdapter.class);
        adapter.setOnItemClick((view, position) -> {
            Map<String, String> map = adapter.getList().get(position);
            String url = map.get("url");
            if (onClickHistoryUrl != null) {
                dismiss();
                onClickHistoryUrl.onClickHistoryUrl(url);
            }
        });
        updateUI();
    }

    private void updateUI() {
        FindMultiExecutor<HistoryRecordEntity> allAsync = LitePal.findAllAsync(HistoryRecordEntity.class);
        allAsync.listen(entityList -> {
            ArrayList<Map<String, String>> list = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                HistoryRecordEntity entity = entityList.get(i);
                map.put("title", entity.getTitle());
                map.put("url", entity.getUrl());
                map.put("time", entity.getTime() + "");
                list.add(map);
            }
            RVHelper.notifyAdapterRefresh(list, rv);
        });

    }


    public void setOnClickHistoryUrl(OnClickHistoryUrl onClickHistoryUrl) {
        this.onClickHistoryUrl = onClickHistoryUrl;
    }

    public interface OnClickHistoryUrl {
        void onClickHistoryUrl(String url);
    }

}
