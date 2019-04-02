package com.test.util.custom_view.fragment;

import android.view.View;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
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
        cityPickerDialogFragment.setOnConfirmListener(() -> ToastUtil.showShort(activity,cityPickerDialogFragment.getCityPicker().getCurrentAreaName()));
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