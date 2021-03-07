package com.adb_forward;

import com.common.utils.LogUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sgll on 2018/12/10.
 */
@SuppressWarnings("unused")
public class ServerThread extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            //设置Android端口为9000
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                try {
                    //从连接队列中取出一个连接，如果没有则等待
                    Socket socket = serverSocket.accept();
                    while (true) {
                        // 发送心跳包，单线程中使用，判断socket是否断开
                        socket.sendUrgentData(0xFF);
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        byte[] buffer = new byte[1024];
                        int bt;
                        StringBuilder text = new StringBuilder();
                        while ((bt = inputStream.read(buffer)) != -1) {
                            text.append(new String(buffer, 0, bt).trim());
                            if (text.toString().endsWith("-vvv")) {
                                break;
                            }
                            LogUtil.d("text:" + text);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
