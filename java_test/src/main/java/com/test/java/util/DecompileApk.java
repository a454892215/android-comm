package com.test.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import brut.androlib.ApkDecoder;

@SuppressWarnings("unused")
public class DecompileApk {

    public static void main(String[] args) {
        //  decompile();
        dex2Jar();

    }

    /**
     * 把所有dex文件转换成jar文件
     */
    private static void dex2Jar() {
        List<String> dexFiles = getDexFiles();
      //  CmdUtil.startCmd("D://");
      //  CmdUtil.startCmd("cd " + DEX_OUT_DIR_PATH + File.separator + "out_jar");
        for (int i = 0; i < dexFiles.size(); i++) {
            CmdUtil.startCmd(DEX2JAR_BAT_PATH + " " + dexFiles.get(i));
        }
    }


    private static final String APK_PATH = "app/build/outputs/apk/product_1/debug/V1.0.2-product_1Debug.apk";
    private static final String APK_OUT_DIR_PATH = "java_test/build/apk";
    private static final String DEX_OUT_DIR_PATH = "reference/DecompileApk/2019_12_18/dex2jar-2.0";
    private static final String DEX2JAR_BAT_PATH = "D:\\work\\AndroidProjects\\CommonLibaray\\Common\\reference\\DecompileApk\\2019_12_18\\dex2jar-2.0\\d2j-dex2jar.bat";

    /**
     * 直接反编译apk 获取资源文件和smali 文件
     */
    private static void decompile() {
        try {
            File apkFile = new File(APK_PATH);
            ApkDecoder decoder = new ApkDecoder();
            decoder.setForceDelete(true);
            decoder.setOutDir(new File(APK_OUT_DIR_PATH));
            decoder.setApkFile(apkFile);
            decoder.decode();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所以dex文件
     */
    private static List<String> getDexFiles() {
        List<String> list = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(APK_PATH);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                if (name.endsWith(".dex")) {
                    String filePath = unzipFileFromZip(zipFile, zipEntry, name, DEX_OUT_DIR_PATH + File.separator);
                    list.add(filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 从zip中解压指定文件
     */
    private static String unzipFileFromZip(ZipFile zipFile, ZipEntry zipEntry, String name, String outDir) {
        String filePath = "";
        try {
            BufferedInputStream bin = new BufferedInputStream(zipFile.getInputStream(zipEntry));
            byte[] inByte = new byte[bin.available()];
            LogUtil.d(name + "  size:" + bin.read(inByte) / 1024 + "kb");
            File outFile = new File(outDir + name);
            File parentFile = outFile.getParentFile();
            if (!parentFile.exists()) {
                LogUtil.d("======创建目录:" + parentFile.getAbsolutePath() + " 是否成功：" + parentFile.mkdirs());
            }
            if (!outFile.exists()) {
                LogUtil.d("======创建输出文件:" + parentFile.getAbsolutePath() + " 是否成功：" + outFile.createNewFile());
            }
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outFile));//文件已经存在会强制覆盖
            bout.write(inByte);
            bin.close();
            bout.close();
            filePath = outFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


}
