package com.common.utils;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LocalCacheUtil {

    private final Application app;
    private final String fileName;

    public LocalCacheUtil(Application app, String fileName) {
        this.app = app;
        this.fileName = fileName;
    }

    public void save(String text) {
        try {
            String log = " 压缩前大小：" + CommFileUtil.getFormatSize(text.getBytes().length);
            text = ZipUtil.zipString(text);
            byte[] bytes = text.getBytes();
            log += " 压缩后大小：" + CommFileUtil.getFormatSize(bytes.length);
            FileOutputStream out = app.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(bytes);
            out.close();
            LogUtil.d("保存文件到本地成功: " + log + " fileName:" + fileName);
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
                String oriText = new String(data);
                String unzipString = ZipUtil.unzipString(oriText);
                String log = "  原size:" + CommFileUtil.getFormatSize(oriText.getBytes().length)
                        + " 解压后size:" + CommFileUtil.getFormatSize(unzipString.getBytes().length) ;
                LogUtil.d("读取文件成功===read:" + read + log);
                return unzipString;
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
