package io.github.houjian.tetris.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author houjian
 */
public class GameUtilsTest {

    @Test
    public void getSleepTimeByLevel() {
        assertEquals(GameUtils.getSleepTimeByLevel(0), 740);
        assertEquals(GameUtils.getSleepTimeByLevel(1), 700);
        assertEquals(GameUtils.getSleepTimeByLevel(2), 660);
        assertEquals(GameUtils.getSleepTimeByLevel(3), 620);
        assertEquals(GameUtils.getSleepTimeByLevel(4), 580);
        assertEquals(GameUtils.getSleepTimeByLevel(5), 540);
        assertEquals(GameUtils.getSleepTimeByLevel(6), 500);
        assertEquals(GameUtils.getSleepTimeByLevel(7), 460);
        assertEquals(GameUtils.getSleepTimeByLevel(8), 420);
        assertEquals(GameUtils.getSleepTimeByLevel(9), 380);
        assertEquals(GameUtils.getSleepTimeByLevel(10), 340);
        assertEquals(GameUtils.getSleepTimeByLevel(11), 300);
        assertEquals(GameUtils.getSleepTimeByLevel(12), 260);
        assertEquals(GameUtils.getSleepTimeByLevel(13), 220);
        assertEquals(GameUtils.getSleepTimeByLevel(14), 180);
        assertEquals(GameUtils.getSleepTimeByLevel(15), 140);
        assertEquals(GameUtils.getSleepTimeByLevel(16), 100);
        assertEquals(GameUtils.getSleepTimeByLevel(17), 100);
    }
}