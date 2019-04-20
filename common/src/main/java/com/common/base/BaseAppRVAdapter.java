package com.common.base;

import android.content.Context;
import com.common.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.util.List;

public class BaseAppRVAdapter extends BaseRVAdapter<Object> {

    public BaseAppRVAdapter(Context activity, int itemLayoutId, List<Object> list) {
        super(activity, itemLayoutId, list);
    }

    public static BaseAppRVAdapter getInstance(Context context, List<?> list, Class<? extends BaseAppRVAdapter> typeClass) {
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
