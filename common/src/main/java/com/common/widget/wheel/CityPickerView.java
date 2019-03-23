package com.common.widget.wheel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.common.R;
import com.common.comm.AssertsResourceLoader;
import com.common.entity.ProvinceEntity;
import com.common.helper.GsonHelper;
import com.common.utils.LogUtil;

import java.util.Arrays;
import java.util.List;

public class CityPickerView extends FrameLayout {
    Context context;

    public CityPickerView(@NonNull Context context) {
        this(context, null);
    }

    public CityPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CityPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        String text = AssertsResourceLoader.getText(context, "china_city.txt");
        ProvinceEntity[] provinceArr = GsonHelper.getEntity(text, ProvinceEntity[].class);
        List<ProvinceEntity> provinceList = Arrays.asList(provinceArr);
        List<ProvinceEntity.CityListBean> cityList = provinceList.get(0).getCityList();
        List<ProvinceEntity.AreaListBean> areaList = cityList.get(0).getAreaList();

        printTextLength(provinceList);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_city_picker, this, true);
        WheelPicker wheel_view_province = view.findViewById(R.id.wheel_view_1);
        WheelPicker wheel_view_city = view.findViewById(R.id.wheel_view_2);
        WheelPicker wheel_view_area = view.findViewById(R.id.wheel_view_3);
        wheel_view_province.setData(provinceList);
        wheel_view_city.setData(cityList);
        wheel_view_area.setData(areaList);

        wheel_view_province.setOnItemSelectedListener((picker, data, position) -> {
            List<ProvinceEntity.CityListBean> cityList_current = ((ProvinceEntity) data).getCityList();
            List<ProvinceEntity.AreaListBean> areaList_current = cityList_current.get(0).getAreaList();
            wheel_view_city.setSelectedItemPosition(0);
            wheel_view_city.setData(cityList_current);

            wheel_view_area.setSelectedItemPosition(0);
            wheel_view_area.setData(areaList_current);


        });
        wheel_view_city.setOnItemSelectedListener((picker, data, position) -> {
            List<ProvinceEntity.AreaListBean> areaList_current = ((ProvinceEntity.CityListBean) data).getAreaList();
            wheel_view_area.setSelectedItemPosition(0);
            wheel_view_area.setData(areaList_current);

        });
    }


    private void printTextLength(List<ProvinceEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            ProvinceEntity provinceEntity = list.get(i);
            String name = provinceEntity.getName();
          //  LogUtil.d("   省级地名是：" + name + " 长度是：" + name.length());
            List<ProvinceEntity.CityListBean> cityList = provinceEntity.getCityList();
            for (int j = 0; j < cityList.size(); j++) {
                ProvinceEntity.CityListBean cityListBean = cityList.get(j);
                String name1 = cityListBean.getName();
                int length1 = name1.length();
                if (length1 >= 10) {
                    LogUtil.e("   市级地名是：" + name1 + " 长度是：" + length1);
                }

                List<ProvinceEntity.AreaListBean> areaList = cityListBean.getAreaList();
                for (int k = 0; k < areaList.size(); k++) {
                    ProvinceEntity.AreaListBean areaListBean = areaList.get(k);
                    String name2 = areaListBean.getName();
                    int length = name2.length();
                    if (length > 10) {
                        LogUtil.e("省级地名是：" + name + "市级地名：" + name1 + "   县级地名是：" + name2 + " 长度是：" + name2.length());
                    }
                }
            }
        }
    }

}
