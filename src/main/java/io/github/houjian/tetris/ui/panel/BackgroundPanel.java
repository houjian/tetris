package io.github.houjian.tetris.ui.panel;

import io.github.houjian.tetris.config.WindowConfig;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BasePanel;

import java.awt.*;

/**
 * 背景
 *
 * @author houjian
 */
public class BackgroundPanel extends BasePanel {

    public BackgroundPanel(WindowConfig window, Game game) {
        super(game);

        this.setBounds(0, 0, window.getWidth(), window.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 画背景图片
        Image img = ImageManager.getBackground(this.game.getLevel());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
