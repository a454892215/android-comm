package adbforword;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AdbForwardStart {

    public static void main(String[] args) {
        try {
            //将PC端8000端口的数据, 转发到Android端的9000端口上
            Runtime.getRuntime().exec("adb forward tcp:8000 tcp:9000");
            System.out.println("连接手机成功...");
            Socket socket = new Socket("127.0.0.1", 8000);
            receiveInfo(socket);
          //  sendInfo(socket);
        } catch (Exception e) {
            System.out.println("设置端口转发失败");
            e.printStackTrace();
        }
    }

    private static void sendInfo(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.next();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg);
            dos.flush();
        }
    }

    private static void receiveInfo(Socket socket) {
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    byte[] buffer = new byte[10 * 1024 * 6];
                    int len = dis.read(buffer);
                    if (len > 0) {
                        System.out.println("\n接收到：" + new String(buffer, 0, len, StandardCharsets.UTF_8));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

}
