package io.github.houjian.tetris.ui.panel;

import io.github.houjian.tetris.config.WindowConfig;
import io.github.houjian.tetris.controller.GameController;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BasePanel;
import io.github.houjian.tetris.ui.component.HoverButton;
import io.github.houjian.tetris.ui.listener.ButtonListener;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * 标题
 *
 * @author houjian
 */
@Slf4j
public class TitlePanel extends BasePanel {

    public TitlePanel(WindowConfig window,
                      Game game,
                      GameController controller,
                      ContentPanel contentPanel) {
        super(game);

        this.setBounds(0, 0, window.getWidth(), window.getTitleHeight());

        final Image logo = ImageManager.loadImage("icon/icon.png");
        final Image settingNormal = ImageManager.loadImage("icon/setting_normal.png");
        final Image settingHover = ImageManager.loadImage("icon/setting_hover.png");
        final Image settingPressed = ImageManager.loadImage("icon/setting_press.png");
        final Image closeNormal = ImageManager.loadImage("icon/close_normal.png");
        final Image closeHover = ImageManager.loadImage("icon/close_hover.png");
        final Image closePressed = ImageManager.loadImage("icon/close_press.png");

        JLabel labelLogo = new JLabel(new ImageIcon(logo));
        labelLogo.setBounds(8, (window.getTitleHeight() - logo.getHeight(null)) / 2, logo.getWidth(null), logo.getHeight(null));

        HoverButton btnClose = new HoverButton(closeNormal, closeHover, closePressed);
        HoverButton btnSetting = new HoverButton(settingNormal, settingHover, settingPressed);
        btnClose.setLocation(getWidth() - btnClose.getWidth() - 1, 1);
        btnSetting.setLocation(getWidth() - btnClose.getWidth() - 1 - btnSetting.getWidth() - 1, 1);

        this.add(labelLogo);
        this.add(btnClose);
        this.add(btnSetting);

        btnClose.addMouseListener(new ButtonListener(btnClose, controller::stop));
        btnSetting.addMouseListener(new ButtonListener(btnSetting, contentPanel::toggle));
    }
}
