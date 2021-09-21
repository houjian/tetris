package io.github.houjian.tetris.model;

import lombok.Getter;

/**
 * 颜色枚举
 *
 * @author houjian
 */
@Getter
public enum Color {

    WHITE(0),
    RED(1),
    ORANGE(2),
    YELLOW(3),
    GREEN(4),
    CYAN(5),
    BLUE(6),
    PURPLE(7),
    GRAY(8);

    /**
     * 该颜色在 block.png 图片中的索引值
     */
    private final int index;

    Color(int index) {
        this.index = index;
    }
}
