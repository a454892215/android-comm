package com.easyui.swing.demo;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Author: Pan
 * 2021/3/8
 * Description:
 */
class Test {

    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame jFrame = new JFrame();
        jFrame.setTitle("测试标题");
        jFrame.setSize(300, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.show();
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}
