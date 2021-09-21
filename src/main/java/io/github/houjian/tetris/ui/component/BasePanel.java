package io.github.houjian.tetris.ui.component;

import io.github.houjian.tetris.model.Game;

import javax.swing.*;

/**
 * @author houjian
 */
public abstract class BasePanel extends JPanel {

    protected final Game game;

    protected BasePanel(Game game) {
        this.game = game;
        // 透明
        this.setOpaque(false);
        // 清空布局
        this.setLayout(null);
    }
}
