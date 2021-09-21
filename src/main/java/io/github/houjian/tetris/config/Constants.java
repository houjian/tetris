package io.github.houjian.tetris.config;

import java.io.File;

/**
 * @author houjian
 */
public class Constants {

    public static final String DIR_CONFIG = "config";
    public static final String DIR_DATA = "data";
    public static final String DIR_RESOURCE = "resource";

    public static final String FILE_CONFIG = "game.xml";
    public static final String FILE_PLAYER = "player.dat";

    public static final String PATH_CONFIG_FILE = DIR_CONFIG + File.separator + FILE_CONFIG;
    public static final String PATH_PLAYER_SCORE_RECORD = DIR_DATA + File.separator + FILE_PLAYER;

    public static final int SCORE_RECORD_SHOW_SIZE = 5;
}
