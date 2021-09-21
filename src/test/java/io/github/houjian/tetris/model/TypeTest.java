package io.github.houjian.tetris.model;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * @author houjian
 */
public class TypeTest {

    @Test
    public void getPoints() {
        for (Type type : Type.values()) {
            Point[] oldPoints = type.getPoints();
            Point[] newPoints = type.getPoints();
            assertNotSame(oldPoints, newPoints);
            assertEquals(oldPoints.length, newPoints.length);

            for (int i = 0; i < oldPoints.length; i++) {
                assertNotSame(oldPoints[i], newPoints[i]);
            }
        }
    }
}