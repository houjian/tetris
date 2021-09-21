package io.github.houjian.tetris.service.impl;

import io.github.houjian.tetris.config.Configuration;
import io.github.houjian.tetris.dao.PlayerDao;
import io.github.houjian.tetris.model.Color;
import io.github.houjian.tetris.model.*;
import io.github.houjian.tetris.service.GameService;
import io.github.houjian.tetris.util.GameUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 游戏主要逻辑
 *
 * @author houjian
 */
public class GameServiceImpl implements GameService {

    private final Configuration configuration;
    private final Game game;
    private final PlayerDao playerDao;
    private final ThreadLocalRandom random;

    public GameServiceImpl(Configuration configuration, Game game, PlayerDao playerDao) {
        this.configuration = configuration;
        this.game = game;
        this.playerDao = playerDao;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public boolean keyUp() {
        // 方形禁止旋转
        Block block = this.game.getBlock();
        if (Type.O.equals(block.getType())) {
            return false;
        }

        Point[] points = block.getPoints();

        synchronized (game) {
            // 判断能否旋转
            for (int i = 1; i < points.length; i++) {
                int newX = points[0].y + points[0].x - points[i].y;
                int newY = points[0].y - points[0].x + points[i].x;

                if (isPointOutOfMatrix(newX, newY)) {
                    return false;
                }
            }

            // 执行旋转
            for (int i = 1; i < points.length; i++) {
                int newX = points[0].y + points[0].x - points[i].y;
                int newY = points[0].y - points[0].x + points[i].x;

                points[i].x = newX;
                points[i].y = newY;
            }
        }

        return true;
    }

    @Override
    public boolean keyDown() {
        // 向下移动一格
        boolean moveSuccess = moveBlock(1, 0);
        if (moveSuccess) {
            return true;
        }

        // 不能移动，活动方块固化到地图中
        Block block = this.game.getBlock();
        for (Point p : block.getPoints()) {
            this.game.getMatrix()[p.x][p.y] = block.getColor();
        }

        // 生成新方块
        this.game.setBlock(new Block(this.game.getNextType()));
        this.game.setNextType(Type.values()[random.nextInt(Type.values().length)]);

        // 消行
        int line = removeLine();
        if (line > 0) {
            addScore(line);
        }

        // 游戏是否失败
        if (isFailure()) {
            this.game.setFailure(true);
            if (this.game.getScore() != 0) {
                this.playerDao.save(Player.builder()
                        .score(this.game.getScore())
                        .time(System.currentTimeMillis())
                        .build());
            }
        }

        return false;
    }

    @Override
    public boolean keyLeft() {
        return moveBlock(0, -1);
    }

    @Override
    public boolean keyRight() {
        return moveBlock(0, 1);
    }

    @Override
    public boolean keyEnter() {
        if (game.isStart() && !game.isFailure()) {
            game.setPause(!game.isPause());
        }
        return true;
    }

    @Override
    public boolean keySpace() {
        for (; ; ) {
            boolean result = keyDown();
            if (!result) {
                break;
            }
        }
        return true;
    }

    @Override
    public boolean keyControl() {
        // 作弊
        this.addScore(new Random().nextInt(4) + 1);
        return true;
    }

    @Override
    public boolean keyShift() {
        this.game.setShowShadow(!this.game.isShowShadow());
        return true;
    }

    /**
     * 移动活动方块
     *
     * @param x 行号
     * @param y 列号
     * @return 不能移动返回false
     */
    private boolean moveBlock(int x, int y) {
        Block block = this.game.getBlock();

        boolean cannotMove = Arrays.stream(block.getPoints())
                .anyMatch(p -> isPointOutOfMatrix(p.x + x, p.y + y));
        if (cannotMove) {
            return false;
        }

        synchronized (game) {
            for (Point point : block.getPoints()) {
                point.x += x;
                point.y += y;
            }
        }

        return true;
    }

    /**
     * 判断坐标是否超出地图边界，或者和地图中已存在方块重叠
     *
     * @param newX 移动后的x坐标
     * @param newY 移动后的y坐标
     * @return 超出边界返回true
     */
    private boolean isPointOutOfMatrix(int newX, int newY) {
        return newX < 0 || newX >= game.getHeight() || newY < 0 || newY >= game.getWidth()
                || game.getMatrix()[newX][newY] != null;
    }

    /**
     * 消行
     *
     * @return 返回消除的行数
     */
    private int removeLine() {
        int line = 0;
        Color[][] matrix = this.game.getMatrix();

        for (int x = 0; x < matrix.length; x++) {
            if (Arrays.stream(matrix[x]).allMatch(Objects::nonNull)) {
                execRemove(x);
                ++line;
            }
        }

        return line;
    }

    /**
     * 执行消行操作
     *
     * @param rowNumber 行号
     */
    private void execRemove(int rowNumber) {
        Color[][] matrix = this.game.getMatrix();

        for (int y = 0; y < matrix[rowNumber].length; y++) {
            for (int x = rowNumber; x > 0; x--) {
                matrix[x][y] = matrix[x - 1][y];
            }
        }
    }

    /**
     * 根据行数加分
     *
     * @param line 行数
     */
    private void addScore(int line) {
        // 加分
        game.setScore(game.getScore() + configuration.getScore().getOrDefault(line, 0));

        // 加消行数
        int removeLine = game.getRemoveLine();
        game.setRemoveLine(removeLine + line);

        int level = configuration.getGame().getLevel();
        if (removeLine % level + line >= level) {
            // 等级加1
            this.game.setLevel(this.game.getLevel() + 1);
            // 游戏加速
            game.setSleepTime(GameUtils.getSleepTimeByLevel(this.game.getLevel()));
        }
    }

    /**
     * 判断游戏是否失败
     *
     * @return 失败返回true
     */
    private boolean isFailure() {
        Point[] points = this.game.getBlock().getPoints();
        Color[][] matrix = this.game.getMatrix();

        for (Point point : points) {
            if (matrix[point.x][point.y] != null) {
                return true;
            }
        }

        return false;
    }
}
