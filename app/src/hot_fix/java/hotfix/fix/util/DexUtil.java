package hotfix.fix.util;

public class DexUtil {

    //dx.bat 文件全路径
    private static final String DEX_FULL_PATH = "C:\\Users\\llpp\\AppData\\Local\\Android\\Sdk\\build-tools\\29.0.2\\dx.bat";
    //class 文件路径
    private static final String CLASS_DIR = "D:\\work\\AndroidProjects\\CommonLibaray\\Common\\app\\hot_fix\\classes";
    //dex输出路径
    private static final String DEX_OUT_DIR = "D:\\work\\AndroidProjects\\CommonLibaray\\Common\\app\\hot_fix\\output.dex";

    public static void main(String[] args) {
        String cmd = DEX_FULL_PATH + " --dex --output =" + DEX_OUT_DIR + " " + CLASS_DIR;
        CmdUtil.startCmd(cmd, null);
      //  DecompileApk.dex2Jar(DEX_OUT_DIR);
    }
}
