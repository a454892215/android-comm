package com.test.java.util;

import java.io.File;

import brut.androlib.ApkDecoder;

public class DecompileApk {

    public static void main(String[] args) {
        decompile();
    }

    /**
     * 直接反编译apk 获取资源文件和smali 文件
     */
    private static void decompile() {
        try {
            File apkFile = new File("app/build/outputs/apk/product_1/debug/V1.0.2-product_1Debug.apk");
            ApkDecoder decoder = new ApkDecoder();
            decoder.setForceDelete(true);
            decoder.setOutDir(new File("java_test/build/apk"));
            decoder.setApkFile(apkFile);
            decoder.decode();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除文件，可以是文件或文件夹
     * @param fileName：要删除的文件名
     * @return 删除成功返回true，否则返回false
     */


}
