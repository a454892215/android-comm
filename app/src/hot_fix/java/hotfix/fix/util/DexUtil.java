package hotfix.fix.util;

public class DexUtil {
    private static final String DEX_FULL_PATH = "C:\\Users\\llpp\\AppData\\Local\\Android\\Sdk\\build-tools\\29.0.2\\dx.bat";
    private static final String CLASS_DIR = "D:\\work\\AndroidProjects\\CommonLibaray\\Common\\java_test\\build\\classes\\java\\main";
    private static final String DEX_OUT_DIR = "D:\\work\\AndroidProjects\\CommonLibaray\\Common\\java_test\\build\\output.dex";

    public static void main(String[] args) {
        String cmd = DEX_FULL_PATH + " --dex --output =" + DEX_OUT_DIR + " " + CLASS_DIR;
        CmdUtil.startCmd(cmd, null);
      //  DecompileApk.dex2Jar(DEX_OUT_DIR);
    }
}
