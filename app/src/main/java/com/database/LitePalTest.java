package com.database;

import com.common.utils.LitePalUtil;
import com.common.utils.LogUtil;

import org.litepal.LitePal;

import java.util.List;

/**
 * Author: Pan
 * 2021/7/30
 * Description:
 */
@SuppressWarnings("unused")
public class LitePalTest {

    public static void save() {
        LitePalTestEntity last = LitePal.findLast(LitePalTestEntity.class);
        if (last != null) {
            LitePalTestEntity entity = new LitePalTestEntity();
            entity.setId(last.getId() + 1);
            entity.setName("我的ID是：" + entity.getId());
        } else {
            LogUtil.i("last为null");
        }


    }

    public static void findAll() {
        List<LitePalTestEntity> list = LitePalUtil.findAll(LitePalTestEntity.class);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            LitePalTestEntity entity = list.get(i);
            LogUtil.d(entity.toString());
        }
    }

    public static void findLast() {
        LitePalTestEntity last = LitePalUtil.findLast(LitePalTestEntity.class);
        if (last != null) {
            LogUtil.d(last.toString());
        } else {
            LogUtil.i("last为null");
        }
    }

}
