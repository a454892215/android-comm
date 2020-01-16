package com.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

public class CommFileUtil {

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getFormatSize(double size) {
        double kb = size / 1024;
        double mb = kb / 1024;
        if (mb < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kb));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gb = mb / 1024;
        if (gb < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(mb));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gb / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gb));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    //只创建目的文件的一级父路径
    public static boolean copy(String formFilePath, String toFilePath) {
        try {
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(formFilePath)));
            byte[] inByte = new byte[bin.available()];
            File outFile = new File(toFilePath);
            File parentFile = outFile.getParentFile();
            if (!parentFile.exists()) {
                LogUtil.d("======创建输出最后一级目录:" + parentFile.getAbsolutePath() + " 是否成功：" + parentFile.mkdirs());
            }
            /*if (!outFile.exists()) {
                LogUtil.d("======创建输出文件:" + parentFile.getAbsolutePath() + " 是否成功：" + outFile.createNewFile());
            }*/
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outFile));//文件已经存在会强制覆盖
            bout.write(inByte);
            bin.close();
            bout.close();
            return true;
        } catch (Exception e) {
          LogUtil.e(e);
        }
        return false;
    }

}
