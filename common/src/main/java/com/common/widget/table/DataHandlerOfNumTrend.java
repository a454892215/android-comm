package com.common.widget.table;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;

/**
 * Author:  Pan
 * CreateDate: 2019/1/5 9:46
 * Description: 号码走势数据处理
 */

public class DataHandlerOfNumTrend extends DataHandler {
    DataHandlerOfNumTrend(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String[] getBodyRowName(String[] keys_1, Map<String, String> map) {
        String[] rightNames = new String[keys_1.length];
        for (int j = 0; j < keys_1.length; j++) {
            String key = keys_1[j];
            String value = map.get(key);
            if (value == null) {
                String[] resultNum = map.get(key_resultInfo).split(",");
                value = resultNum[index_tab_level_2];
                value = value.equals(key) ? value : "";
                if (value.matches("0[0-9]")) value = value.substring(1, value.length());
                if (!TextUtils.isEmpty(value)) trendPointList.add(value);
            }
            if (isRemovePrefix_0 && key_resultInfo.equals(key)) {
                value = value.replaceAll("0([0-9])", "$1");
            }
            rightNames[j] = value;
        }
        return rightNames;
    }

}
