package com.common.utils;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    public static void appendTextToBugsFile(Context context, String text) {
        try {
            String name = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            text = "\r\n\r\n======================\r\n\r\n" + name + "==\r\n==" +
                    text + "\r\n\r\n======================\r\n\r\n";
            File rootDirFile = context.getExternalCacheDir();
            File dirFile = new File(rootDirFile, "bugs");
            if (!dirFile.exists()) {
                boolean mkdirs = dirFile.mkdirs();
                LogUtil.d("==============mkdirs:" + mkdirs);
            }
            File fileName = new File(dirFile, new SimpleDateFormat("yy_MM_dd", Locale.getDefault()).format(new Date()) + ".txt");
            if (!fileName.exists()) {
                boolean newFile = fileName.createNewFile();
                LogUtil.d("==============newFile:" + newFile);
            }
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(text);
            fileWriter.close();
            LogUtil.d("追加bugs文本成功");
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
