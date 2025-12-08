package dexutil;


import java.io.File;

public class DexUtil {

    //dx.bat 文件全路径
  //  private static final String DEX_FULL_PATH = "C:\\Users\\llpp\\AppData\\Local\\Android\\Sdk\\build-tools\\29.0.2\\dx.bat";
    private static final String DEX_FULL_PATH = "C:\\Users\\llpp\\AppData\\Local\\Android\\Sdk\\build-tools\\32\\dx.bat";
    //class 文件路径
    private static final String CLASS_DIR = "app/hot_fix/classes";
    //dex输出路径
    private static final String DEX_OUT_DIR = "app/hot_fix/" + "app_dex.dex";

    public static void main(String[] args) {
        String cmd = DEX_FULL_PATH + " --dex --output =" + DEX_OUT_DIR + " " + new File(CLASS_DIR).getAbsolutePath();
        CmdUtil.startCmd(cmd, null);
     //   DecompileApk.dex2Jar(new File(DEX_OUT_DIR).getAbsolutePath());
    }
}
