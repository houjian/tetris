package io.github.houjian.tetris.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * 按钮hover效果
 *
 * @author houjian
 */
public class HoverButton extends JButton {

    /**
     * 正常显示图标
     */
    private final Icon normal;
    /**
     * 鼠标划过图标
     */
    private final Icon hover;
    /**
     * 鼠标按下图标
     */
    private final Icon pressed;

    public HoverButton(Image normal, Image hover, Image pressed) {
        super(new ImageIcon(normal));

        this.normal = new ImageIcon(normal);
        this.hover = new ImageIcon(hover);
        this.pressed = new ImageIcon(pressed);

        this.setBorder(null);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setSize(normal.getWidth(null), normal.getHeight(null));
    }

    public void changeToNormal() {
        this.setIcon(this.normal);
    }

    public void changeToHover() {
        this.setIcon(this.hover);
    }

    public void changeToPressed() {
        this.setIcon(this.pressed);
    }
}
