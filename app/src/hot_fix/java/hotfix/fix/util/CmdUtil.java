package hotfix.fix.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

class CmdUtil {

    static void startCmd(String cmd, File dir) {
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(cmd, null, dir);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(child.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();
            child.waitFor();
            LogUtil.d("cmd:" + cmd);
            LogUtil.d("======:" + "out:" + sb.toString() + "cmd finished");

        } catch (Exception e) {
            LogUtil.e("======:" + e);
        }
    }
}
