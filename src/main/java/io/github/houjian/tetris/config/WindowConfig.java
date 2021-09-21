package io.github.houjian.tetris.config;

import lombok.Builder;
import lombok.Getter;

/**
 * @author houjian
 */
@Getter
@Builder
public class WindowConfig {

    /**
     * 标题
     */
    private final String title;
    /**
     * 标题高度
     */
    private final int titleHeight;
    /**
     * 窗口宽度
     */
    private final int width;
    /**
     * 窗口高度
     */
    private final int height;
}
