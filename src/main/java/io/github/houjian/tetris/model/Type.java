package io.github.houjian.tetris.model;

import io.github.houjian.tetris.util.GameUtils;
import lombok.Getter;

import java.awt.*;

import static io.github.houjian.tetris.model.Color.*;

/**
 * 方块类型枚举
 *
 * @author houjian
 */
@Getter
public enum Type {

    I(new Point[]{new Point(0, 1), new Point(0, 0), new Point(0, 2), new Point(0, 3)}, RED, 3),
    O(new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}, ORANGE, 4),
    T(new Point[]{new Point(1, 1), new Point(0, 1), new Point(1, 0), new Point(1, 2)}, YELLOW, 3),
    L(new Point[]{new Point(1, 1), new Point(0, 2), new Point(1, 0), new Point(1, 2)}, GREEN, 3),
    J(new Point[]{new Point(1, 1), new Point(0, 0), new Point(1, 0), new Point(1, 2)}, CYAN, 3),
    S(new Point[]{new Point(1, 1), new Point(0, 1), new Point(0, 2), new Point(1, 0)}, BLUE, 3),
    Z(new Point[]{new Point(1, 1), new Point(0, 0), new Point(0, 1), new Point(1, 2)}, PURPLE, 3);

    /**
     * 4个小方块
     */
    private final Point[] points;
    /**
     * 方块颜色
     */
    private final Color color;
    /**
     * 起始位置
     */
    private final int startY;

    Type(Point[] points, Color color, int startY) {
        this.points = points;
        this.color = color;
        this.startY = startY;
    }

    /**
     * 返回深拷贝，避免外部修改
     *
     * @return 方块位置数组
     */
    public Point[] getPoints() {
        return GameUtils.deepCopy(this.points);
    }
}
