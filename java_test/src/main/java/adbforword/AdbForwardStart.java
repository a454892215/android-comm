package adbforword;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class AdbForwardStart {

    private static Socket socket;

    public static void main(String[] args) {
        try {
            //将PC端8000端口的数据, 转发到Android端的9000端口上
            Process exec = Runtime.getRuntime().exec("adb forward tcp:8000 tcp:9000");
            System.out.println("adb执行完毕");
            socket = new Socket("127.0.0.1", 8000);
            AdbForwardStart start = new AdbForwardStart();
            start.receiveInfo();
            start.sendInfo("我是来自PC的信息");
            start.inputAndSend();
        } catch (Exception e) {
            System.out.println("设置端口转发失败");
            e.printStackTrace();
        }
    }

    public static final String end_mark = ":==END";

    private void inputAndSend() throws IOException {
        Executors.newSingleThreadExecutor().execute(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入...");
            while (true) {
                sendInfo(scanner.next());
                System.out.println("请输入...");
            }
        });
    }

    private void sendInfo(String msg) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg + end_mark);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveInfo() {
      Executors.newSingleThreadExecutor().execute(()->{
          try {
              InputStream inputStream = socket.getInputStream();
              byte[] buffer = new byte[1024 * 1024 * 10];
              int index;
              StringBuilder text = new StringBuilder();
              while ((index = inputStream.read(buffer)) != -1) {
                  text.append(new String(buffer, 0, index).trim());
                  if (text.toString().endsWith(end_mark)) {
                      System.out.println("收到信息 :" + text);
                      text.delete(0, text.length());
                      buffer = new byte[1024 * 1024 * 10];
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
    }

}
