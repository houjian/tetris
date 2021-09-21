package io.github.houjian.tetris.util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author houjian
 */
public class IOUtilsTest {

    private static final String FILE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "tetris";
    private static final String FILE_CONTENT = "test";

    @Test
    public void writeObjectAndReadObject() {
        boolean result = IOUtils.writeObject(FILE_CONTENT, FILE_PATH);
        assertTrue(result);
        assertTrue(new File(FILE_PATH).exists());

        Object object = IOUtils.readObject(FILE_PATH);
        assertEquals(FILE_CONTENT, object);
    }

    @Test
    public void writeFileAndReadFile() {
        boolean result = IOUtils.writeFile(FILE_PATH, FILE_CONTENT);
        assertTrue(result);
        assertTrue(new File(FILE_PATH).exists());

        String content = IOUtils.readFile(FILE_PATH);
        assertEquals(FILE_CONTENT, content);
    }
}