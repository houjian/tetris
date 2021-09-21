package io.github.houjian.tetris.ui;

import io.github.houjian.tetris.config.WindowConfig;

import javax.swing.*;

/**
 * 主窗口
 *
 * @author houjian
 */
public class MainFrame extends JFrame {

    public MainFrame(WindowConfig window) {
        // 点击右上角关闭按钮的时候退出程序
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 让主窗体大小固定
        this.setResizable(false);
        // 不显示窗口默认的标题栏
        this.setUndecorated(true);
        // 清除布局管理器
        this.setLayout(null);
        // 设置窗口大小
        this.setSize(window.getWidth(), window.getHeight());
        // 窗口居中显示
        this.setLocationRelativeTo(null);
    }
}
