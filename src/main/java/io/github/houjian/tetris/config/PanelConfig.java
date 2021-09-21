package io.github.houjian.tetris.config;

import lombok.Builder;
import lombok.Getter;

/**
 * @author houjian
 */
@Getter
@Builder
public class PanelConfig {

    /**
     * 面板名称
     */
    private final String name;
    /**
     * 面板x坐标
     */
    private final int x;
    /**
     * 面板y坐标
     */
    private final int y;
    /**
     * 面板宽度
     */
    private final int width;
    /**
     * 面板高度
     */
    private final int height;
}
