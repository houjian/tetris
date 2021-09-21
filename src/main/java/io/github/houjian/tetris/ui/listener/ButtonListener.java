package io.github.houjian.tetris.ui.listener;

import io.github.houjian.tetris.ui.component.HoverButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 按钮动作监控
 *
 * @author houjian
 */
@Slf4j
public class ButtonListener extends MouseAdapter {

    /**
     * 按钮
     */
    private final HoverButton button;
    /**
     * 按钮点击执行操作
     */
    private final Runnable action;
    /**
     * 按钮是否按下
     */
    private boolean pressed;

    public ButtonListener(HoverButton button, Runnable action) {
        this.button = button;
        this.action = action;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        log.debug("mouseEntered ({}, {})", e.getX(), e.getY());

        this.button.changeToHover();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        log.debug("mouseExited ({}, {})", e.getX(), e.getY());

        if (!this.pressed) {
            this.button.changeToNormal();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        log.debug("mousePressed ({}, {})", e.getX(), e.getY());

        this.pressed = true;
        this.button.changeToPressed();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        log.debug("mouseReleased ({}, {})", e.getX(), e.getY());

        this.pressed = false;

        final Point point = e.getPoint();
        final Dimension size = this.button.getSize();

        log.debug("size=({}, {}), point=({}, {})", size.width, size.height, point.x, point.y);

        if (0 <= point.x && point.x < size.width && 0 <= point.y && point.y < size.height) {
            if (action != null) {
                action.run();
            }

            this.button.changeToHover();
        } else {
            this.button.changeToNormal();
        }
    }
}
