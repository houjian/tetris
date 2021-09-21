package io.github.houjian.tetris.ui.game;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BaseLayerPanel;

import java.awt.*;

/**
 * 下一个
 *
 * @author houjian
 */
public class NextPanel extends BaseLayerPanel {

    public NextPanel(Configuration configuration, Game game) {
        super(configuration, game);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.isStart()) {
            drawImageInCenter(ImageManager.getType(this.game.getNextType()), g);
        }
    }

    /**
     * 正中绘制图片
     */
    protected void drawImageInCenter(Image img, Graphics g) {
        int imgX = (getWidth() - img.getWidth(null)) >> 1;
        int imgY = (getHeight() - img.getHeight(null)) >> 1;
        g.drawImage(img, imgX, imgY, null);
    }
}
