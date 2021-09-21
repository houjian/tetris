package io.github.houjian.tetris.util;

import java.awt.*;

/**
 * 游戏相关工具
 *
 * @author houjian
 */
public class GameUtils {

    /**
     * 根据等级返回线程睡眠毫秒值
     * <pre>y = -40x + 740</pre>
     *
     * @param level 等级
     * @return 线程睡眠毫秒值
     */
    public static long getSleepTimeByLevel(int level) {
        long sleep = (-40L * level + 740);
        return Math.max(sleep, 100L);
    }

    /**
     * 数组深拷贝
     *
     * @param points 坐标点
     * @return 深拷贝副本
     */
    public static Point[] deepCopy(Point[] points) {
        final int length = points.length;

        Point[] copiedPoints = new Point[length];
        for (int i = 0; i < length; i++) {
            copiedPoints[i] = (Point) points[i].clone();
        }

        return copiedPoints;
    }
}
