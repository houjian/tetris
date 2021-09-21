package io.github.houjian.tetris.config;

import lombok.Builder;
import lombok.Getter;

/**
 * @author houjian
 */
@Getter
@Builder
public class GameConfig {

    /**
     * 方块区域宽度
     */
    private final int width;
    /**
     * 方块区域高度
     */
    private final int height;
    /**
     * 升级配置
     */
    private final int level;
}
