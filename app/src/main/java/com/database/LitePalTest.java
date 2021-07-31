package com.database;

import android.os.SystemClock;

import com.common.utils.LitePalUtil;
import com.common.utils.LogUtil;

import org.litepal.LitePal;

import java.util.List;
import java.util.concurrent.Executors;

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


    public static void save100w() {
        Executors.newSingleThreadExecutor().execute(() -> {
            long start = SystemClock.elapsedRealtime();
            LitePalTestEntity last = LitePal.findLast(LitePalTestEntity.class);
            for (int i = 0; i < 1000000; i++) {
                if (i % 10000 == 0) {
                    LogUtil.d(" 进度：" + (i / 10000));
                }
                long id = last == null ? 0 : last.getId() + 1;
                LitePalTestEntity entity = new LitePalTestEntity();
                entity.setId(id);
                entity.setName("我的ID是：" + entity.getId());
                entity.save();
                last = entity;
            }
            LogUtil.d("花费时间：" + (SystemClock.elapsedRealtime() - start));
        });
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
        Executors.newSingleThreadExecutor().execute(() -> {
            long start = SystemClock.elapsedRealtime();
            List<LitePalTestEntity> list = LitePalUtil.findAll(LitePalTestEntity.class);
            int size = list.size();
            LogUtil.d("总数据size:" + size);
            for (int i = size > 10 ? size - 10 : 0; i < size; i++) {
                LitePalTestEntity entity = list.get(i);
                LogUtil.d(entity.toString());
            }
            LogUtil.d("花费时间：" + (SystemClock.elapsedRealtime() - start));
        });

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
