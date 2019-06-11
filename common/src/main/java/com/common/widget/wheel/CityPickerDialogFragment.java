package com.common.widget.wheel;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.common.R;
import com.common.dialog.BottomDialogFragment;
import com.common.listener.OnConfirmListener;

public class CityPickerDialogFragment extends BottomDialogFragment implements View.OnClickListener {


    OnConfirmListener onConfirmListener;
    private CityPickerView city_picker;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_city_picker;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.tv_ok).setOnClickListener(this);
        findViewById(R.id.tv_no).setOnClickListener(this);
        city_picker = findViewById(R.id.city_picker);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_ok) {
            dismiss();
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
        } else if (id == R.id.tv_no) {
            dismiss();
        }
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public CityPickerView getCityPicker() {
        return city_picker;
    }
}
