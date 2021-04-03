package adbforword;

import com.test.java.util.LogUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
              //  String charset = getEncoding(line);
                line = new String(line.getBytes("gbk"), "gbk");
                LogUtil.d(line);
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

    public static String getEncoding(String str) {
        try {
            String encode = "GB2312";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "ISO-8859-1";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "UTF-8";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "GBK";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
