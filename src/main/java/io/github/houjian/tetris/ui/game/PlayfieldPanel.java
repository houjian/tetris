package io.github.houjian.tetris.ui.game;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Block;
import io.github.houjian.tetris.model.Color;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BaseLayerPanel;
import io.github.houjian.tetris.ui.component.HoverButton;
import io.github.houjian.tetris.ui.listener.ButtonListener;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * 游戏区域
 *
 * @author houjian
 */
@Slf4j
public class PlayfieldPanel extends BaseLayerPanel {

    /**
     * 单个小方块的宽度
     */
    private final int blockWidth;
    /**
     * 单个小方块的高度
     */
    private final int blockHeight;
    /**
     * 开始按钮
     */
    private final HoverButton btnStart;
    /**
     * 暂停按钮
     */
    private final HoverButton btnContinue;

    public PlayfieldPanel(Configuration configuration, Game game) {
        super(configuration, game);

        this.blockWidth = ImageManager.block.getWidth(null) / Color.values().length;
        this.blockHeight = ImageManager.block.getHeight(null);

        final Image startNormal = ImageManager.start;
        final Image startHover = ImageManager.scaledImage(startNormal, 1.1);
        final Image startPressed = ImageManager.scaledImage(startNormal, 0.95);
        btnStart = new HoverButton(startNormal, startHover, startPressed);
        btnStart.setBounds((this.getWidth() - startHover.getWidth(null)) >> 1,
                (this.getHeight() - startHover.getHeight(null)) >> 1,
                startHover.getWidth(null), startHover.getHeight(null));
        this.add(btnStart);
        btnStart.setVisible(true);
        btnStart.addMouseListener(new ButtonListener(btnStart, this::start));

        final Image continueNormal = ImageManager.pause;
        final Image continueHover = ImageManager.scaledImage(ImageManager.continued, 1.1);
        final Image continuePressed = ImageManager.scaledImage(ImageManager.continued, 0.95);
        btnContinue = new HoverButton(continueNormal, continueHover, continuePressed);
        btnContinue.setBounds((this.getWidth() - continueHover.getWidth(null)) >> 1,
                (this.getHeight() - continueHover.getHeight(null)) >> 1,
                continueHover.getWidth(null), continueHover.getHeight(null));
        this.add(btnContinue);
        btnContinue.setVisible(false);
        btnContinue.addMouseListener(new ButtonListener(btnContinue, this::continued));
    }

    public void start() {
        log.debug("start button");

        btnStart.setVisible(false);
        game.setStart(true);
        game.setPause(false);
        if (game.isFailure()) {
            game.init();
        }
        repaint();
    }

    public void continued() {
        log.debug("continue button");

        btnContinue.setVisible(false);
        game.setPause(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.isStart()) {
            this.drawShadow(g);
            this.drawMatrix(g);
            this.drawBlock(g);

            if (game.isFailure()) {
                this.btnStart.setVisible(true);
            } else {
                this.btnContinue.setVisible(game.isPause());
            }
        } else {
            this.btnStart.setVisible(true);
        }

        this.requestFocus();
    }

    /**
     * 绘制阴影部分
     *
     * @param g 画笔
     */
    private void drawShadow(Graphics g) {
        if (!this.game.isShowShadow()) {
            return;
        }

        Point point = this.game.getBlock().range();
        g.drawImage(ImageManager.shadow,
                point.x * blockWidth + border,
                border,
                (point.y - point.x + 1) * blockWidth,
                getHeight() - border * 2,
                null);
    }

    /**
     * 绘制游戏地图矩阵
     *
     * @param g 画笔
     */
    private void drawMatrix(Graphics g) {
        Color[][] matrix = this.game.getMatrix();
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                Color color = matrix[x][y];
                if (color == null) {
                    continue;
                }
                drawColor(g, x, y, this.game.isFailure() ? Color.GRAY : color);
            }
        }
    }

    /**
     * 绘制活动方块
     *
     * @param g 画笔
     */
    private void drawBlock(Graphics g) {
        Block block = this.game.getBlock();
        for (Point point : block.getPoints()) {
            drawColor(g, point.x, point.y, block.getColor());
        }
    }

    /**
     * 在(x,y)处绘制颜色方块
     *
     * @param g     画笔
     * @param x     x坐标
     * @param y     y坐标
     * @param color 颜色
     */
    private void drawColor(Graphics g, int x, int y, Color color) {
        g.drawImage(ImageManager.block,
                y * blockWidth + border,
                x * blockHeight + border,
                (y + 1) * blockWidth + border,
                (x + 1) * blockHeight + border,
                color.getIndex() * blockWidth,
                0,
                (color.getIndex() + 1) * blockWidth,
                blockHeight,
                null);
    }
}
