package io.github.houjian.tetris.ui.panel;

import io.github.houjian.tetris.config.WindowConfig;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BasePanel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * 主内容面板
 *
 * @author houjian
 */
@Slf4j
public class ContentPanel extends BasePanel {

    /**
     * 是否是设置面板
     */
    private boolean setting;
    /**
     * 卡片布局管理器，切换游戏界面与设置界面
     */
    private final CardLayout cardLayout;

    public ContentPanel(WindowConfig window, Game game, GamePanel gamePanel, SettingPanel settingPanel) {
        super(game);

        final int titleHeight = window.getTitleHeight();
        this.setBounds(0, titleHeight, window.getWidth(), window.getHeight() - titleHeight);

        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.add(gamePanel.getClass().getName(), gamePanel);
        this.add(settingPanel.getClass().getName(), settingPanel);

        // 默认展示游戏面板
        this.cardLayout.show(this, gamePanel.getClass().getName());
        this.setting = false;
    }

    /**
     * 游戏面板与设置面板来回切换
     */
    public void toggle() {
        if (this.setting) {
            cardLayout.show(this, GamePanel.class.getName());
            log.debug("show game panel");
        } else {
            cardLayout.show(this, SettingPanel.class.getName());
            log.debug("show setting panel");
            this.game.setPause(true);
        }

        this.setting = !this.setting;
    }
}
