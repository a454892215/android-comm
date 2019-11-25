package com.common.bugs;

import android.content.Context;

import com.common.utils.LogUtil;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class LocalBugHelper {

    static void appendTextToBugsFile(Context context, String text) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                synchronized (LocalBugHelper.class) {
                    String name = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    String info = "\r\n\r\n======================\r\n\r\n" + "----" + name + "----\r\n" +
                            text + "\r\n======================\r\n\r\n";
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
                    fileWriter.write(info);
                    fileWriter.close();
                    LogUtil.d("追加bugs文本成功");
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        });

    }

    public static String getFormatStackTrace(StackTraceElement[] elements) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : elements) {
            builder.append(element).append("\r\n");
        }
        return builder.toString();
    }
}
