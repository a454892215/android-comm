package com.easyui.swing.demo;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Author: Pan
 * 2021/3/8
 * Description:
 */
class JFrameTest {

    private static void jFrameDemo() {
        JFrame jFrame = getJFrame();
        jFrame.setVisible(true);
    }

    //JFrame 用来设计类似于 Windows 系统中窗口形式的界面。JFrame 是 Swing 组件的顶层容器，该类继承了 AWT 的 Frame 类，支持 Swing 体系结构的高级 GUI 属性。
    private static JFrame getJFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("测试标题");
        jFrame.setSize(800, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation( (screenSize.width - jFrame.getWidth()) / 2,(screenSize.height - jFrame.getHeight()) / 2);
        return jFrame;
    }

    //JPanel 是一种中间层容器，它能容纳组件并将组件组合在一起，但它本身必须添加到其他容器中使用。
    private static void jPanelDemo() {
        JFrame jFrame = getJFrame();    //创建一个JFrame对象
        jFrame.setSize(800, 500);    //设置窗口大小和位置
        JPanel jPanel=new JPanel();    //创建一个JPanel对象
        jPanel.setBackground(Color.GREEN);    //设置背景色
        JLabel jLabel=new JLabel("这是放在JPanel上的标签");    //创建一个标签
        jPanel.add(jLabel);    //将标签添加到面板
        jFrame.add(jPanel);    //将面板添加到窗口
        jFrame.setVisible(true);    //设置窗口可见
    }

    public static void main(String[] args) {
      //  jFrameDemo();
        jPanelDemo();
    }
}
