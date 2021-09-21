package io.github.houjian.tetris.ui.component;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.config.PanelConfig;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;

import java.awt.*;

/**
 * @author houjian
 */
public abstract class BaseLayerPanel extends BasePanel {

    /**
     * 面板内边距
     */
    protected final int padding;
    /**
     * 边框宽度
     */
    protected final int border;

    public BaseLayerPanel(Configuration configuration, Game game) {
        super(game);

        // 设置边框
        this.setBorder(new LayerBorder(configuration.getBorder()));
        // 设置位置
        final PanelConfig panel = configuration.getPanel(this.getClass().getSimpleName());
        this.setBounds(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());

        this.padding = configuration.getPadding();
        this.border = configuration.getBorder();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Image img = ImageManager.layer;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getHeight(null), img.getHeight(null), null);
    }

    private static final Image NUMBER = ImageManager.number;
    private static final int NUMBER_WIDTH = NUMBER.getWidth(null) / 12;
    private static final int NUMBER_HEIGHT = NUMBER.getHeight(null);

    /**
     * 根据不同方式对齐数字
     */
    protected void drawNumberAlign(int score, int y, Align alignType, Graphics g) {
        // 获得分数
        String s = String.valueOf(score);
        // 获取长度
        int length = s.length();

        // 确定起始x坐标
        int x = 0;

        switch (alignType) {
            case LEFT:
                x = this.padding;
                break;
            case CENTER:
                x = (getWidth() - NUMBER_WIDTH * length) >> 1;
                break;
            case RIGHT:
                x = getWidth() - this.padding - NUMBER_WIDTH * length;
                break;
            default:
                break;
        }

        // 绘制数字图片
        for (int i = 0; i < length; i++) {
            int num = Integer.parseInt(s.charAt(i) + "");

            g.drawImage(ImageManager.number,
                    x + NUMBER_WIDTH * i, y, x + NUMBER_WIDTH * (i + 1), y + NUMBER_HEIGHT,
                    num * NUMBER_WIDTH, 0, (num + 1) * NUMBER_WIDTH, NUMBER_HEIGHT, null);
        }
    }

    protected enum Align {
        LEFT, RIGHT, CENTER
    }

    private static final Image IMAGE_TROUGH = ImageManager.trough;
    private static final int TROUGH_WIDTH = IMAGE_TROUGH.getWidth(null);
    protected static final int TROUGH_HEIGHT = IMAGE_TROUGH.getHeight(null);
    private static final int TROUGH_BORDER = 2;

    private static final Image IMAGE_RECT = ImageManager.rect;
    private static final int RECT_WIDTH = IMAGE_RECT.getWidth(null);
    private static final int RECT_HEIGHT = IMAGE_RECT.getHeight(null);

    /**
     * 在（x，y）绘制宽度为w的值槽
     */
    protected void drawTrough(String name, int number, int x, int y, int w, int value, int maxValue, Graphics g) {
        drawTroughRect(x, y, w, value, maxValue, g);

        drawTitleInTrough(name, y, g);
        drawStringInTrough(String.valueOf(number), y, w, g);
    }

    protected void drawTrough(Image img, int x, int y, int w, int value, int maxValue, Graphics g) {
        double percent = drawTroughRect(x, y, w, value, maxValue, g);

        drawTitleInTrough(img, y, w, g);
        drawStringInTrough(String.format("%.2f%%", percent * 100), y, w, g);
    }

    private double drawTroughRect(int x, int y, int w, double value, int maxValue, Graphics g) {
        createThrough(x, y, w, g);

        // 当前分数相对最大值的百分比
        double percent = (maxValue == 0) ? 1.0 : value / (double) maxValue;
        percent = Math.min(Math.max(percent, 0.0), 1.0);

        // 方块图片中百分比宽度的位置
        int index = (int) (RECT_WIDTH * percent);
        index = Math.min(RECT_WIDTH - 1, index);

        int width = (int) ((w - TROUGH_BORDER * 2) * percent);
        g.drawImage(ImageManager.rect, x + TROUGH_BORDER, y + TROUGH_BORDER, x + TROUGH_BORDER + width, y + TROUGH_HEIGHT - TROUGH_BORDER, index, 0, index + 1, RECT_HEIGHT, null);
        return percent;
    }

    private void drawTitleInTrough(Image title, int y, int w, Graphics g) {
        Dimension sample = ImageManager.calculateSample(title.getWidth(null), title.getHeight(null), w - TROUGH_BORDER * 2, TROUGH_HEIGHT - TROUGH_BORDER * 2 - 2);

        int startX = this.padding + TROUGH_BORDER + 1;
        int startY = y + TROUGH_BORDER + ((TROUGH_HEIGHT - TROUGH_BORDER * 2 - sample.height) >> 1);

        g.drawImage(title, startX, startY, sample.width, sample.height, null);
    }

    private void drawTitleInTrough(String title, int y, Graphics g) {
        g.setFont(new Font("黑体", Font.BOLD, 20));
        g.setColor(Color.WHITE);

        int startX = this.padding + TROUGH_BORDER + 1;

        g.drawString(title, startX, y + TROUGH_BORDER + 20);
    }

    private void drawStringInTrough(String percent, int y, int w, Graphics g) {
        Dimension sample = ImageManager.calculateSample(NUMBER_WIDTH, NUMBER_HEIGHT, w - TROUGH_BORDER * 2, TROUGH_HEIGHT - TROUGH_BORDER * 2);

        int length = percent.length();
        int startX = getWidth() - this.padding - TROUGH_BORDER - 2 - sample.width * length;
        int startY = y + TROUGH_BORDER + ((TROUGH_HEIGHT - TROUGH_BORDER * 2 - sample.height) >> 1) + 1;

        for (int i = 0; i < length; i++) {
            int n;

            final char c = percent.charAt(i);
            if (c == '.') {
                n = 10;
            } else if (c == '%') {
                n = 11;
            } else {
                n = Integer.parseInt(c + "");
            }

            g.drawImage(ImageManager.number,
                    startX + sample.width * i, startY, startX + sample.width * (i + 1), startY + sample.height,
                    n * NUMBER_WIDTH, 0, (n + 1) * NUMBER_WIDTH, NUMBER_HEIGHT, null);
        }
    }

    /**
     * 在（x，y）处创建宽度为w的值槽
     */
    private void createThrough(int x, int y, int w, Graphics g) {
        // 左边
        g.drawImage(ImageManager.trough, x, y, x + TROUGH_BORDER, y + TROUGH_HEIGHT, 0, 0, TROUGH_BORDER, TROUGH_HEIGHT, null);
        // 中间
        g.drawImage(ImageManager.trough, x + TROUGH_BORDER, y, x + w - TROUGH_BORDER, y + TROUGH_HEIGHT, TROUGH_BORDER, 0, TROUGH_WIDTH - TROUGH_BORDER, TROUGH_HEIGHT, null);
        // 右边
        g.drawImage(ImageManager.trough, x + w - TROUGH_BORDER, y, x + w, y + TROUGH_HEIGHT, TROUGH_WIDTH - TROUGH_BORDER, 0, TROUGH_WIDTH, TROUGH_HEIGHT, null);
    }
}
