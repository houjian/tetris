package io.github.houjian.tetris.ui.game;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BaseLayerPanel;

import java.awt.*;

/**
 * 等级
 *
 * @author houjian
 */
public class LevelPanel extends BaseLayerPanel {

    public LevelPanel(Configuration configuration, Game game) {
        super(configuration, game);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Image level = ImageManager.level;
        int imgX = (getWidth() - level.getWidth(null)) >> 1;
        g.drawImage(level, imgX, this.padding, null);

        int numberY = level.getHeight(null) + this.padding * 2;
        drawNumberAlign(this.game.getLevel(), numberY, Align.CENTER, g);
    }
}
