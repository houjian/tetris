package io.github.houjian.tetris.controller;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.dao.PlayerDao;
import io.github.houjian.tetris.dao.impl.PlayerDaoImpl;
import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.service.GameService;
import io.github.houjian.tetris.service.impl.GameServiceImpl;
import io.github.houjian.tetris.ui.MainFrame;
import io.github.houjian.tetris.ui.game.*;
import io.github.houjian.tetris.ui.listener.PlayfieldListener;
import io.github.houjian.tetris.ui.panel.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author houjian
 */
@Slf4j
public class GameController {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean running;

    private final Game game;
    private final GameService gameService;
    private final MainFrame mainFrame;

    public GameController(Configuration configuration) {
        this.game = new Game(configuration);
        this.mainFrame = new MainFrame(configuration.getWindow());

        PlayerDao playerDao = new PlayerDaoImpl();
        this.gameService = new GameServiceImpl(configuration, game, playerDao);

        PlayfieldPanel playfieldPanel = new PlayfieldPanel(configuration, game);
        NextPanel nextPanel = new NextPanel(configuration, game);
        LevelPanel levelPanel = new LevelPanel(configuration, game);
        ScorePanel scorePanel = new ScorePanel(configuration, game);
        RecordPanel recordPanel = new RecordPanel(configuration, game, playerDao);

        BackgroundPanel backgroundPanel = new BackgroundPanel(configuration.getWindow(), game);
        GamePanel gamePanel = new GamePanel(game, playfieldPanel, nextPanel, levelPanel, scorePanel, recordPanel);
        SettingPanel settingPanel = new SettingPanel(game);
        ContentPanel contentPanel = new ContentPanel(configuration.getWindow(), game, gamePanel, settingPanel);
        TitlePanel titlePanel = new TitlePanel(configuration.getWindow(), game, this, contentPanel);

        playfieldPanel.addKeyListener(new PlayfieldListener(mainFrame, game, gameService));

        mainFrame.setContentPane(backgroundPanel);
        mainFrame.add(titlePanel);
        mainFrame.add(contentPanel);
        mainFrame.setVisible(true);

        this.executor.execute(this::run);
    }

    public void start() {
        log.info("game starting...");
    }

    public void stop() {
        log.info("game stopping...");

        this.mainFrame.dispose();
        this.running = false;
        executor.shutdown();
    }

    public void run() {
        this.running = true;

        while (running) {
            try {
                TimeUnit.MILLISECONDS.sleep(this.game.getSleepTime());
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            if (this.game.isStart() && !this.game.isPause() && !this.game.isFailure()) {
                log.debug("game thread down");
                this.gameService.keyDown();
                this.mainFrame.repaint();
            }
        }
    }
}
