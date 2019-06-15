package com.test.util.custom_view.rv.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.common.GlideApp;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseViewHolder;
import com.common.utils.CastUtil;
import com.common.utils.ViewHolder;
import com.test.util.R;

import java.util.Arrays;
import java.util.List;

public class SnapTestAdapter extends BaseAppRVAdapter {

    public SnapTestAdapter(Context activity, List<Object> list) {
        super(activity, R.layout.adapter_snap_test, CastUtil.cast(Arrays.asList(imgUrl)));
        isLoopMode = true;
    }

    //   private int[] colors = {R.color.light_purple, R.color.sky_blue};
    private static String[] imgUrl = {"http://img17.3lian.com/d/file/201702/21/8f8a5c670f68613382cb043d1ad2fe05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/1fa7ef2fbf14cb7640ea50de1914cd05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/44b2c79be750dcc69f919bc786cbd173.jpg"
            , "http://img17.3lian.com/d/file/201702/21/834c9af2d7b02b74a1d9d44b527c53ff.jpg"
            , "http://img17.3lian.com/d/file/201702/21/8c49c4da75a889cc3c4ceb211a2adaa3.jpg"};

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) return;
        ImageView iv = ViewHolder.get(holder.itemView, R.id.iv);
        GlideApp.with(context).load(imgUrl[position % 5]).into(iv);
        // Map<String, String> map = CastUtil.cast(list.get(position));
        //  String name = map.get("name");
        //   tv.setText(name);
        // holder.itemView.setBackgroundColor(context.getResources().getColor(colors[position % colors.length]));
    }
}
