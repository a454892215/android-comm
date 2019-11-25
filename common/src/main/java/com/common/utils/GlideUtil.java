package com.common.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.util.concurrent.Executors;

public class GlideUtil {

    public static String getDefaultCacheDirSize(Context context) {
        try {
            String dir = context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
            long folderSize = FileUtil.getFolderSize(new File(dir));
            return FileUtil.getFormatSize(folderSize);
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return "0.00Byte";
    }

    public static void clearDiskCacheSize(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Glide.get(context).clearDiskCache();
            } catch (Exception e) {
                LogUtil.e(e);
            }
        });

    }
}
