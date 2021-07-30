package com.common.utils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * Author: Pan
 * 2021/7/30
 * Description:
 */
@SuppressWarnings("unused")
public class LitePalUtil {

    public static void saveOrUpdate(LitePalSupport entity) {
        boolean save = entity.save();
        if (!save) {
            throw new RuntimeException("LitePal 保存数据异常");
        }
    }

    public static void delete(Class<? extends LitePalSupport> clazz, long Id) {
        int rows = LitePal.delete(clazz, Id);
        LogUtil.d(" delete 影响的行数是：" + rows);
    }


    public static <T extends LitePalSupport> T find(Class<T> clazz, long Id) {
        return LitePal.find(clazz, Id);
    }

    public static <T extends LitePalSupport> T findLast(Class<T> clazz) {
        return LitePal.findLast(clazz);
    }

    public static <T extends LitePalSupport> T findFirst(Class<T> clazz) {
        return LitePal.findFirst(clazz);
    }

    public static <T extends LitePalSupport> List<T> findAll(Class<T> clazz) {
        return LitePal.findAll(clazz);
    }


    public static void update(LitePalSupport entity, long Id) {
        int rows = entity.update(Id);
        LogUtil.d(" update 影响的行数是：" + rows);
    }
}
