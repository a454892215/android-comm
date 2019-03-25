package com.test.util.fragment;

import android.view.View;
import android.widget.Button;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
import com.common.widget.wheel.CityPickerView;
import com.test.util.R;

public class CityPickerFragment extends BaseFragment implements View.OnClickListener {
    private CityPickerView mCityPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.include_frag_city_picker;
    }

    @Override
    protected void initView(View rootView) {
        mCityPicker = rootView.findViewById(R.id.city_picker);
        Button mBtnOk = rootView.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                ToastUtil.showShort(activity, mCityPicker.getCurrentAreaName());
                break;
        }
    }
}
