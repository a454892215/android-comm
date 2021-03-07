package com.adb_forward;

import com.common.utils.LogUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by sgll on 2018/12/10.
 */
@SuppressWarnings("unused")
public class AdbForwardThread extends Thread {

    private Socket socket;

    public static AdbForwardThread instance = new AdbForwardThread();

    public static AdbForwardThread getInstance() {
        return instance;
    }

    @Override
    public void run() {
        super.run();
        try {
            LogUtil.d("AdbForwardThread==启动=======:");
            //设置Android端口为9000
            ServerSocket serverSocket = new ServerSocket(9000);
            try {
                //从连接队列中取出一个连接，如果没有则等待
                socket = serverSocket.accept();
                LogUtil.d("发现新连接：" + socket.getInetAddress().getHostName());
                //  socket.sendUrgentData(0xFF);  // 发送心跳包，单线程中使用，判断socket是否断开
                receiveInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void receiveInfo() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024 * 1024 * 10];
        int index;
        StringBuilder text = new StringBuilder();
        while ((index = inputStream.read(buffer)) != -1) {
            text.append(new String(buffer, 0, index).trim());
            if (text.toString().endsWith(end_mark)) {
                LogUtil.d("收到信息 :" + text);
                text.delete(0, text.length());
                buffer = new byte[1024 * 1024 * 10];
            }
        }
    }

    public static final String end_mark = ":==END";

    public void send(String text) {
        try {
            if (socket == null) {
                LogUtil.e("socket是null");
                return;
            }
            if (!socket.isClosed()) {
                text = text + end_mark;
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            } else {
                LogUtil.e("socket是关闭状态");
            }

        } catch (IOException e) {
            LogUtil.e(e);
        }
    }

}
