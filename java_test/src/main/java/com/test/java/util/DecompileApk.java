package com.test.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import brut.androlib.ApkDecoder;

@SuppressWarnings("unused")
public class DecompileApk {

    public static void main(String[] args) {
        //  decompile();
        getDexFiles();
    }


    private static final String APK_PATH = "app/build/outputs/apk/product_1/debug/V1.0.2-product_1Debug.apk";
    private static final String APK_OUT_DIR_PATH = "java_test/build/apk";
    private static final String DEX_OUT_DIR_PATH = "java_test/build/dex";

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
    private static void getDexFiles() {
        try {
            ZipFile zipFile = new ZipFile(APK_PATH);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                if (name.endsWith(".dex")) {
                    unzipFileFromZip(zipFile, zipEntry, name, DEX_OUT_DIR_PATH + File.separator);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从zip中解压指定文件
     */
    private static void unzipFileFromZip(ZipFile zipFile, ZipEntry zipEntry, String name, String outDir) {
        try {
            InputStream in = zipFile.getInputStream(zipEntry);
            BufferedInputStream bin = new BufferedInputStream(in);
            byte[] inByte = new byte[bin.available()];
            int read = bin.read(inByte);
            bin.close();
            LogUtil.d(name + "  size:" + read / 1024 + "kb");
            File outFile = new File(outDir + name);
            File parentFile = outFile.getParentFile();
            if (!parentFile.exists()) {
                LogUtil.d("======创建目录:" + parentFile.getAbsolutePath() + " 是否成功：" + parentFile.mkdirs());
            }
            if (!outFile.exists()) {
                LogUtil.d("======创建输出文件:" + parentFile.getAbsolutePath() + " 是否成功：" + outFile.createNewFile());
            }
            FileOutputStream out = new FileOutputStream(outFile); //文件已经存在会强制覆盖
            BufferedOutputStream bout = new BufferedOutputStream(out);
            bout.write(inByte);
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
