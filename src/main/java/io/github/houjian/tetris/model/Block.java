package io.github.houjian.tetris.model;

import lombok.Data;

import java.awt.*;

/**
 * 活动方块
 *
 * @author houjian
 */
@Data
public class Block {

    private final Type type;
    private final Point[] points;
    private final Color color;

    public Block(Type type) {
        this.type = type;
        this.points = type.getPoints();
        for (Point point : this.points) {
            point.y += type.getStartY();
        }
        this.color = type.getColor();
    }

    /**
     * 获取活动方块最左最右y坐标
     *
     * @return (最左y坐标, 最右y坐标)
     */
    public Point range() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Point point : points) {
            if (point.y < min) {
                min = point.y;
            }
            if (point.y > max) {
                max = point.y;
            }
        }

        return new Point(min, max);
    }
}
