package com.common.base;

import android.content.Context;

import com.common.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public class BaseAppRVAdapter extends BaseRVAdapter<Map<String, String>> {

    public BaseAppRVAdapter(Context activity, int itemLayoutId, List<Map<String, String>> list) {
        super(activity, itemLayoutId, list);
      //  setEmptyLayoutId(R.layout.item_rv_empty_data);
    }

    public static BaseAppRVAdapter getInstance(Context context, int itemLayoutId, List<Map<String, String>> list, String classFullName) {
        BaseAppRVAdapter adapter = null;
        try {
            Class<?> typeClass = Class.forName(classFullName);
            Class[] parameterTypes = {Context.class, int.class, List.class};
            Constructor constructor = typeClass.getConstructor(parameterTypes);
            Object[] parameters = {context, itemLayoutId, list};
            adapter = (BaseAppRVAdapter) constructor.newInstance(parameters);
        } catch (Exception e) {
            LogUtil.e("动态反射创建对象 异常================");
            e.printStackTrace();
        }

        return adapter;
    }
}
