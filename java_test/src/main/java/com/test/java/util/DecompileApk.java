package com.test.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import brut.androlib.ApkDecoder;

@SuppressWarnings("unused")
public class DecompileApk {

    private static final String APK_PATH = "java_test/build/app.apk";
    private static final String JD_GUI_PATH = "reference/DecompileApk/2019_12_18/jd-gui.exe";

    //反编译后的apk输出目录
    private static final String APK_FILES_OUT_DIR_PATH = "java_test/build/apk";

    //dex文件输出目录
    private static final String DEX_OUT_DIR_PATH = "reference/DecompileApk/2019_12_18/dex2jar-2.0";

    //d2j-dex2jar.bat 文件路径
    private static final String DEX2JAR_BAT_PATH = DEX_OUT_DIR_PATH + "/d2j-dex2jar.bat";

    //dex反编译成jar文件输出目录
    private static final String JAR_OUT_DIR_PATH = "java_test/build/jarOut";

    public static void main(String[] args) {
        LogUtil.d("请输入序号：1.反编译apk获取资源文件，2.反编译apk获取源码, 9.退出");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1:
                    decompile();
                    LogUtil.d("反编译apk获取资源文件==============完成");
                    break;
                case 2:
                    dex2Jar();
                    LogUtil.d("反编译apk获取源码===============完成");
                    break;
                case 9:
                    return;
                default:
                    LogUtil.e("非法输入");
                    break;
            }
        }


    }

    /**
     * 把所有dex文件转换成jar文件,并且使用JD_GUI打开所有的jar
     */
    private static void dex2Jar() {
        File jarOutDir = new File(JAR_OUT_DIR_PATH);
        FileUtil.deleteDir(jarOutDir.getAbsolutePath());
        FileUtil.createDir(jarOutDir);
        List<String> dexFiles = getDexFiles();
        for (int i = 0; i < dexFiles.size(); i++) {
            CmdUtil.startCmd(new File(DEX2JAR_BAT_PATH).getAbsolutePath() + " " + dexFiles.get(i), jarOutDir);
        }
        CmdUtil.startCmd("explorer " + new File(JAR_OUT_DIR_PATH).getAbsolutePath(), null);
        StringBuilder all_jar_file_path = new StringBuilder();
        List<String> jarPathList = FileUtil.getAllChildFileAbsolutePathList(jarOutDir.getAbsolutePath());
        for (int i = 0; i < jarPathList.size(); i++) {
            if (jarPathList.get(i).endsWith(".jar")) {
                all_jar_file_path.append(jarPathList.get(i)).append(" ");
            }
        }
        LogUtil.d("all_jar_file_path:" + all_jar_file_path);
        CmdUtil.startCmd(new File(JD_GUI_PATH).getAbsolutePath() + " " + all_jar_file_path, null);
    }

    /**
     * 直接反编译apk 获取资源文件和smali 文件
     */
    private static void decompile() {
        try {
            File apkFile = new File(APK_PATH);
            ApkDecoder decoder = new ApkDecoder();
            decoder.setForceDelete(true);
            decoder.setOutDir(new File(APK_FILES_OUT_DIR_PATH));
            decoder.setApkFile(apkFile);
            decoder.decode();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有dex文件
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
