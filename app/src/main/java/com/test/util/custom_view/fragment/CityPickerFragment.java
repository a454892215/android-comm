package com.test.util.custom_view.fragment;

import android.view.View;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
import com.common.widget.CommonTextView;
import com.common.widget.wheel.CityPickerDialogFragment;
import com.common.widget.wheel.CityPickerView;
import com.test.util.R;

public class CityPickerFragment extends BaseFragment implements View.OnClickListener {
    private CityPickerView mCityPicker;
    private CityPickerDialogFragment cityPickerDialogFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_frag_city_picker;
    }

    @Override
    protected void initView() {
        mCityPicker = findViewById(R.id.city_picker);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        cityPickerDialogFragment = new CityPickerDialogFragment();
        cityPickerDialogFragment.setOnConfirmListener(() -> ToastUtil.showShort(activity, cityPickerDialogFragment.getCityPicker().getCurrentAreaName()));

        CommonTextView tv = findViewById(R.id.tv);
       /* tv.setLinearGradient(new LinearGradient(0, 0, 0, tv.getPaint().getTextSize(),
                Color.parseColor("#ff0000"), Color.parseColor("#c19c68"), Shader.TileMode.CLAMP));*/
       // tv.setText(BgSpan.getStrokeSpan("我是动态设置的文字", getResources().getColor(R.color.red_1),
           //     Color.WHITE, activity.dp_1, activity.dp_1 * 3, activity.dp_1 * 5, activity.dp_1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                ToastUtil.showShort(activity, mCityPicker.getCurrentAreaName());
                break;
            case R.id.btn_dialog:
                cityPickerDialogFragment.show(fm, cityPickerDialogFragment.getClass().getName());
                break;
        }
    }

}
