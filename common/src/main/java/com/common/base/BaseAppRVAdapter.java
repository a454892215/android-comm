package com.common.base;

import android.content.Context;

import com.common.R;
import com.common.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public class BaseAppRVAdapter extends BaseRVAdapter<Map<String, String>> {

    public BaseAppRVAdapter(Context activity, int itemLayoutId, List<Map<String, String>> list) {
        super(activity, itemLayoutId, list);
    }

    public static BaseAppRVAdapter getInstance(Context context, List<Map<String, String>> list, Class<? extends BaseAppRVAdapter> typeClass) {
        BaseAppRVAdapter adapter = null;
        try {
            Class[] parameterTypes = {Context.class, List.class};
            Constructor constructor = typeClass.getConstructor(parameterTypes);
            Object[] parameters = {context, list};
            adapter = (BaseAppRVAdapter) constructor.newInstance(parameters);
        } catch (Exception e) {
            LogUtil.e("动态反射创建对象 异常================");
            e.printStackTrace();
        }

        return adapter;
    }
}
