package com.test.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

class CmdUtil {

    static void startCmd(String cmd, File dir) {
        try {
            Process child = Runtime.getRuntime().exec(cmd, null, dir);
            String info = getInfo(child);
            child.waitFor();
            LogUtil.d("cmd:" + cmd);
            LogUtil.d("======:" + "out:" + info + "cmd finished");

        } catch (Exception e) {
            LogUtil.e("======:" + e);
        }
    }

    private static String getInfo(Process process) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        bufferedReader.close();
        return sb.toString();
    }
}
