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
        long id = last == null ? 0 : last.getId() + 1;
        LitePalTestEntity entity = new LitePalTestEntity();
        entity.setId(id);
        entity.setName("我的ID是：" + entity.getId());
        boolean save = entity.save();
        LogUtil.d("save:" + save + " :" + entity);
    }

    public static void deleteLast() {
        LitePalTestEntity last = LitePalUtil.findLast(LitePalTestEntity.class);
        if (last != null) {
            int rows = last.delete();
            LogUtil.d("删除的数据是：" + last.toString() + "  影响行数：" + rows);
        } else {
            LogUtil.i("无数据");
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
            LogUtil.i("无数据");
        }
    }

}
