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
    private WheelPicker wheel_view_province;
    private WheelPicker wheel_view_city;
    private WheelPicker wheel_view_area;
    private List<ProvinceEntity> currentProvinceList;
    private List<ProvinceEntity.CityListBean> currentCityList;
    private List<ProvinceEntity.AreaListBean> currentAreaList;

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
        currentProvinceList = Arrays.asList(provinceArr);
        currentCityList = currentProvinceList.get(0).getCityList();
        currentAreaList = currentCityList.get(0).getAreaList();
        //  printTextLength(provinceList);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_city_picker, this, true);
        wheel_view_province = view.findViewById(R.id.wheel_view_1);
        wheel_view_city = view.findViewById(R.id.wheel_view_2);
        wheel_view_area = view.findViewById(R.id.wheel_view_3);
        wheel_view_province.setData(currentProvinceList);
        wheel_view_city.setData(currentCityList);
        wheel_view_area.setData(currentAreaList);

        wheel_view_province.setOnItemSelectedListener((picker, data, position) -> {
            ProvinceEntity provinceEntity = (ProvinceEntity) data;
            //  province = provinceEntity.getName();
            currentCityList = provinceEntity.getCityList();
            currentAreaList = currentCityList.get(0).getAreaList();
            wheel_view_city.setSelectedItemPosition(0);
            wheel_view_city.setData(currentCityList);

            wheel_view_area.setSelectedItemPosition(0);
            wheel_view_area.setData(currentAreaList);
        });
        wheel_view_city.setOnItemSelectedListener((picker, data, position) -> {
            ProvinceEntity.CityListBean cityListBean = (ProvinceEntity.CityListBean) data;
            currentAreaList = cityListBean.getAreaList();
            wheel_view_area.setSelectedItemPosition(0);
            wheel_view_area.setData(currentAreaList);

        });

     /*   wheel_view_area.setOnItemSelectedListener((picker, data, position) -> {
            ProvinceEntity.AreaListBean areaListBean = (ProvinceEntity.AreaListBean) data;
        });*/
    }

    public WheelPicker getProvinceWheelView() {
        return wheel_view_province;
    }

    public WheelPicker getCityWheelView() {
        return wheel_view_city;
    }

    public WheelPicker getAreaWheelView() {
        return wheel_view_area;
    }

    public String getCurrentAreaName() {
        String province = null;
        String city = null;
        String area = null;
        try {
            int po_1 = wheel_view_province.getCurrentItemPosition();
            int po_2 = wheel_view_city.getCurrentItemPosition();
            int po_3 = wheel_view_area.getCurrentItemPosition();
            LogUtil.d("currentCityList:" + currentCityList + " currentAreaList: " + currentAreaList + "  po_2:" + po_2 + "  po_3:" + po_3);
            province = currentProvinceList.get(po_1).getName();
            city = currentCityList.get(po_2).getName();
            area = currentAreaList.get(po_3).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return province + "-" + city + "-" + area;
    }

/*    private void printTextLength(List<ProvinceEntity> list) {
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
    }*/

}
