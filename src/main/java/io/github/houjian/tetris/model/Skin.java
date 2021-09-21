package io.github.houjian.tetris.model;

import lombok.Getter;

/**
 * 皮肤
 *
 * @author houjian
 */
@Getter
public enum Skin {
    DEFAULT("default");

    private final String name;

    Skin(String name) {
        this.name = name;
    }
}
