package io.github.houjian.tetris.ui.listener;

import io.github.houjian.tetris.model.Game;
import io.github.houjian.tetris.service.GameService;
import io.github.houjian.tetris.ui.MainFrame;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author houjian
 */
@Slf4j
public class PlayfieldListener extends KeyAdapter {

    private final MainFrame mainFrame;
    private final Game game;
    private final GameService playerService;

    public PlayfieldListener(MainFrame mainFrame,
                             Game game,
                             GameService playerService) {
        this.game = game;
        this.playerService = playerService;
        this.mainFrame = mainFrame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isFailure()) {
            return;
        }

        log.debug("按下键盘: keyCode={}, keyChar={}", e.getKeyCode(), e.getKeyChar());

        boolean result = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                result = this.playerService.keyUp();
                break;
            case KeyEvent.VK_DOWN:
                result = this.playerService.keyDown();
                break;
            case KeyEvent.VK_LEFT:
                result = this.playerService.keyLeft();
                break;
            case KeyEvent.VK_RIGHT:
                result = this.playerService.keyRight();
                break;
            case KeyEvent.VK_ENTER:
                result = this.playerService.keyEnter();
                break;
            case KeyEvent.VK_SPACE:
                result = this.playerService.keySpace();
                break;
            case KeyEvent.VK_SHIFT:
                result = this.playerService.keyShift();
                break;
            case KeyEvent.VK_CONTROL:
                result = this.playerService.keyControl();
                break;
            default:
                log.warn("不支持的按键操作: keyCode={}, keyChar={}", e.getKeyCode(), e.getKeyChar());
                break;
        }

        log.debug("按键执行结果: {}", result);
        this.mainFrame.repaint();
    }
}
