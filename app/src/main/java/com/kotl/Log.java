package com.kotl;

/**
 * Author: Pan
 * 2022/2/8
 * Description:
 */
public class Log {

    public enum Color {
        WHITE(0xffffff), RED(31), GREEN(36), YELLOW(33), DEF(0);
        int colorCode;

        Color(int colorCode) {
            this.colorCode = colorCode;
        }
    }

    public static void d(Object obj) {
        System.out.println("\033[99;5m" + (obj == null ? "null" : obj.toString()) + "\033[0m");
    }

    public static void d(String text, Color color) {
        System.out.println("\033[" + color.colorCode + ";5m" + text + "\033[0m");
    }

    public static void e(String text) {
        System.out.println("\033[31;5m" + text + "\033[0m");
    }

    public static void e(Throwable e) {
        System.out.println("\033[31;5m" + e.toString() + "\033[0m");
    }


    public static void main(String[] args) {
        System.out.println("默认颜色");
        System.out.println("\033[30;5m" + "白色" + "\033[0m");
        System.out.println("\033[31;4m" + "红色" + "\033[0m");
        System.out.println("\033[32;4m" + "黄绿色" + "\033[0m");
        System.out.println("\033[33;4m" + "黄色" + "\033[0m");
        System.out.println("\033[34;4m" + "蓝色" + "\033[0m");
        System.out.println("\033[35;4m" + "紫色" + "\033[0m");
        System.out.println("\033[36;4m" + "青色" + "\033[0m");


    }
}
