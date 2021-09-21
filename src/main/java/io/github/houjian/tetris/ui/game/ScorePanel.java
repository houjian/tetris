package io.github.houjian.tetris.ui.game;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BaseLayerPanel;

import java.awt.*;

/**
 * 得分区域
 *
 * @author houjian
 */
public class ScorePanel extends BaseLayerPanel {

    private final int level;

    public ScorePanel(Configuration configuration, Game game) {
        super(configuration, game);

        level = configuration.getGame().getLevel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Image score = ImageManager.score;
        final Image rmline = ImageManager.rmline;
        final Image exp = ImageManager.exp;

        final int scoreY = this.padding;
        g.drawImage(score, this.padding, scoreY, null);
        drawNumberAlign(this.game.getScore(), scoreY, Align.RIGHT, g);

        final int rmlineY = score.getHeight(null) + this.padding * 2;
        g.drawImage(rmline, this.padding, rmlineY, null);
        drawNumberAlign(this.game.getRemoveLine(), rmlineY, Align.RIGHT, g);

        final int troughY = score.getHeight(null) + rmline.getHeight(null) + this.padding * 3;
        drawTrough(exp, this.padding, troughY, getWidth() - this.padding * 2, this.game.getRemoveLine() % level, level, g);
    }
}
