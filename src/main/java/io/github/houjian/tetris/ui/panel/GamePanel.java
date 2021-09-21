package io.github.houjian.tetris.ui.panel;

import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.ui.component.BasePanel;
import io.github.houjian.tetris.ui.game.*;

/**
 * 游戏内容区域
 *
 * @author houjian
 */
public class GamePanel extends BasePanel {

    public GamePanel(Game game,
                     PlayfieldPanel playfieldPanel,
                     NextPanel nextPanel,
                     LevelPanel levelPanel,
                     ScorePanel scorePanel,
                     RecordPanel recordPanel) {
        super(game);

        this.add(playfieldPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(scorePanel);
        this.add(recordPanel);
    }
}
