package io.github.houjian.tetris.exception;

/**
 * 游戏异常
 *
 * @author houjian
 */
@SuppressWarnings("unused")
public class GameException extends RuntimeException {

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }

    protected GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
