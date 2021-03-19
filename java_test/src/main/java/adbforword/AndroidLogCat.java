package adbforword;

import com.test.java.util.LogUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Author: Pan
 * 2021/3/13
 * Description:
 */
class AndroidLogCat {

    private static void exeCmd() {
        try {
            String ins_1 = "cmd.exe /c dir " + System.getProperty("user.home") + "&&";
            String ins_2 = "adb devices && adb logcat -c && adb logcat -g && adb logcat | find \"LLpp\"";
            Process process = Runtime.getRuntime().exec(ins_1 + ins_2, null, null);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Log:" + line);
            }
            bufferedReader.close();
            System.out.println("执行结果：" + process.waitFor());
        } catch (Exception e) {
         e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        exeCmd();
    }
}
