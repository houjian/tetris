package io.github.houjian.tetris.ui.component;

import io.github.houjian.tetris.core.ImageManager;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * @author houjian
 */
public class LayerBorder extends AbstractBorder {

    /**
     * 边框宽度
     */
    private final int border;

    public LayerBorder(int border) {
        this.border = border;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);

        final Image img = ImageManager.border;

        final int w = img.getHeight(null);
        final int h = img.getHeight(null);
        final int b = this.border;

        // 左上角
        g.drawImage(img, 0, 0, b, b, 0, 0, b, b, null);
        // 中上边框
        g.drawImage(img, b, 0, width - b, b, b, 0, w - b, b, null);
        // 右上角
        g.drawImage(img, width - b, 0, width, b, w - b, 0, w, b, null);
        // 左中边框
        g.drawImage(img, 0, b, b, height - b, 0, b, b, h - b, null);
        // 右中边框
        g.drawImage(img, width - b, b, width, height - b, w - b, b, w, h - b, null);
        // 左下角
        g.drawImage(img, 0, height - b, b, height, 0, h - b, b, h, null);
        // 左中边框
        g.drawImage(img, b, height - b, width - b, height, b, h - b, w - b, h, null);
        // 右下角
        g.drawImage(img, width - b, height - b, width, height, w - b, h - b, w, h, null);
    }
}
