package io.github.houjian.tetris.model;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.config.GameConfig;
import io.github.houjian.tetris.util.GameUtils;
import lombok.Data;

import java.util.Random;

/**
 * 游戏数据对象
 *
 * @author houjian
 */
@Data
public class Game {

    /**
     * 游戏地图列数量
     */
    private int width;
    /**
     * 游戏地图行数量
     */
    private int height;
    /**
     * 游戏地图
     */
    private Color[][] matrix;
    /**
     * 活动方块
     */
    private Block block;
    /**
     * 下一个活动方块的类型
     */
    private Type nextType;
    /**
     * 等级
     */
    private int level;
    /**
     * 分数
     */
    private int score;
    /**
     * 消行
     */
    private int removeLine;
    /**
     * 游戏是否开始
     */
    private boolean start;
    /**
     * 游戏是否失败
     */
    private boolean failure;
    /**
     * 游戏是否暂停
     */
    private boolean pause;
    /**
     * 是否显示阴影
     */
    private boolean showShadow;
    /**
     * 线程睡眠时间
     */
    private long sleepTime;

    public Game(Configuration configuration) {
        GameConfig config = configuration.getGame();
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.init();
    }

    public void init() {
        this.matrix = new Color[this.height][this.width];
        Random random = new Random();
        Type currentType = Type.values()[random.nextInt(Type.values().length)];
        this.block = new Block(currentType);
        this.nextType = Type.values()[random.nextInt(Type.values().length)];
        this.level = 1;
        this.score = 0;
        this.removeLine = 0;
        this.failure = false;
        this.pause = false;
        this.sleepTime = GameUtils.getSleepTimeByLevel(getLevel());
    }
}
