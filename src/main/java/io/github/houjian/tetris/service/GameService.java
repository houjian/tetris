package io.github.houjian.tetris.service;

/**
 * @author houjian
 */
public interface GameService {

    /**
     * 按键逻辑-上键
     *
     * @return 操作结果
     */
    boolean keyUp();

    /**
     * 按键逻辑-下键
     *
     * @return 操作结果
     */
    boolean keyDown();

    /**
     * 按键逻辑-左键
     *
     * @return 操作结果
     */
    boolean keyLeft();

    /**
     * 按键逻辑-右键
     *
     * @return 操作结果
     */
    boolean keyRight();

    /**
     * 按键逻辑-回车键
     *
     * @return 操作结果
     */
    boolean keyEnter();

    /**
     * 按键逻辑-空格键
     *
     * @return 操作结果
     */
    boolean keySpace();

    /**
     * 按键逻辑-Ctrl键
     *
     * @return 操作结果
     */
    boolean keyControl();

    /**
     * 按键逻辑-Shift键
     *
     * @return 操作结果
     */
    boolean keyShift();
}
