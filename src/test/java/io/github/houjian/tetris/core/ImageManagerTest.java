package io.github.houjian.tetris.core;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author houjian
 */
public class ImageManagerTest {

    @Test
    public void calculateSample() {
        Dimension size;

        size = ImageManager.calculateSample(400, 300, 200, 100);
        assertEquals(size.width, 400 / 3);
        assertEquals(size.height, 100);

        size = ImageManager.calculateSample(400, 300, 500, 400);
        assertEquals(size.width, 400);
        assertEquals(size.height, 300);

        size = ImageManager.calculateSample(400, 300, 400, 300);
        assertEquals(size.width, 400);
        assertEquals(size.height, 300);
    }

    @Test
    public void scaledImage() throws Exception {
        try (FileInputStream in = new FileInputStream("resource/logo.png")) {
            BufferedImage image = ImageIO.read(in);
            int width = image.getWidth();
            int height = image.getHeight();

            System.out.println("width: " + width);
            System.out.println("height: " + height);

            Image result = ImageManager.scaledImage(image, 0.8);
            assertEquals(result.getWidth(null), (int) (width * 0.8));
            assertEquals(result.getHeight(null), (int) (height * 0.8));
        }

    }
}