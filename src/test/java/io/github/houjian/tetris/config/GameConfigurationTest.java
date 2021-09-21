package io.github.houjian.tetris.config;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author houjian
 */
public class GameConfigurationTest {

    @Test
    public void loadConfig() {
        Configuration configuration = new Configuration(Constants.PATH_CONFIG_FILE);
        WindowConfig window = configuration.getWindow();
        Map<String, PanelConfig> panels = configuration.getPanels();
        Map<Integer, Integer> score = configuration.getScore();

        assertNotNull(window);
        assertNotNull(panels);
        assertNotNull(score);

        assertEquals(5, panels.size());
        assertEquals(4, score.size());
    }
}