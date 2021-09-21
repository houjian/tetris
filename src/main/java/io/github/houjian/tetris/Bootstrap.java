package io.github.houjian.tetris;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.config.Constants;
import io.github.houjian.tetris.controller.GameController;

/**
 * 启动类
 *
 * @author houjian
 */
public class Bootstrap {

    public static void main(String[] args) {
        new GameController(new Configuration(Constants.PATH_CONFIG_FILE)).start();
    }
}
