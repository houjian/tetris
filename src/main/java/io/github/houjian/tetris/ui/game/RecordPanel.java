package io.github.houjian.tetris.ui.game;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.config.Constants;
import io.github.houjian.tetris.core.ImageManager;
import io.github.houjian.tetris.dao.PlayerDao;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.model.Player;
import io.github.houjian.tetris.ui.component.BaseLayerPanel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 记录区域
 *
 * @author houjian
 */
public class RecordPanel extends BaseLayerPanel {

    private final PlayerDao playerDao;
    private final int startY;
    private final int space;

    public RecordPanel(Configuration configuration, Game game, PlayerDao playerDao) {
        super(configuration, game);

        this.playerDao = playerDao;
        startY = ImageManager.record.getHeight(null) + this.padding * 2;
        int recordSize = Constants.SCORE_RECORD_SHOW_SIZE;
        space = (getHeight() - startY - this.padding - TROUGH_HEIGHT * recordSize) / (recordSize - 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(ImageManager.record, this.padding, this.padding, null);

        List<Player> players = this.playerDao.load();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int troughY = startY + i * (TROUGH_HEIGHT + space);
            drawTrough(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(player.getTime())),
                    player.getScore(), this.padding, troughY, getWidth() - this.padding * 2,
                    this.game.getScore(), player.getScore(), g);
        }
    }
}
