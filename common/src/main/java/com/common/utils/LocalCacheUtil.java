package com.common.utils;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LocalCacheUtil {

    private Application app;
    private String fileName;

    public LocalCacheUtil(Application app, String fileName) {
        this.app = app;
        this.fileName = fileName;
    }

    public void save(String text) {
        try {
            FileOutputStream out = app.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(text.getBytes());
            out.close();
            LogUtil.d("=====保存文件成功========");
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public String get() {
        try {
            File file = new File(app.getFilesDir(), fileName);
            if (file.exists()) {
                FileInputStream in = app.openFileInput(fileName);
                byte[] data = new byte[in.available()];
                int read = in.read(data);
                in.close();
                LogUtil.d("========读取文件成功==============read:" + read);
                return new String(data);
            } else {
                LogUtil.d(file.getAbsolutePath() + " 文件不存在");
            }

        } catch (Exception e) {
            LogUtil.e(e);
        }
        return "";
    }


    /**
     * 测试函数
     */
    public static void test(Application app, String fileName) {
        LocalCacheUtil cacheUtil = new LocalCacheUtil(app, fileName);
        LogUtil.d("==========LocalCacheUtil==========:" + cacheUtil.get());
        cacheUtil.save("我是被保存的文本嘎嘎");
    }

}
