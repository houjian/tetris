package io.github.houjian.tetris.config;

import io.github.houjian.tetris.exception.GameException;
import io.github.houjian.tetris.util.IOUtils;
import io.github.houjian.tetris.util.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author houjian
 */
@Getter
@Slf4j
public class Configuration {

    /**
     * 窗口配置
     */
    private WindowConfig window;
    /**
     * 游戏配置
     */
    private GameConfig game;
    /**
     * 游戏面板配置
     */
    private Map<String, PanelConfig> panels;
    /**
     * 得分配置
     */
    private Map<Integer, Integer> score;
    /**
     * 面板内边距
     */
    private int padding;
    /**
     * 面板边框宽度
     */
    private int border;

    /**
     * 加载配置文件
     *
     * @param path 配置文件路径
     */
    public Configuration(String path) {
        File file = new File(path);
        if (file.exists()) {
            loadExternalConfig(file);
        } else {
            loadInternalConfig();
        }
    }

    private void loadExternalConfig(File file) {
        log.debug("load config from external config file [{}]", file.getAbsolutePath());

        String content = IOUtils.readFile(file);
        if (StringUtils.isNotBlank(content)) {
            parseConfig(content);
        } else {
            loadInternalConfig();
        }
    }

    private void loadInternalConfig() {
        log.debug("load config from internal config file [classpath:{}]", Constants.FILE_CONFIG);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = classLoader.getResourceAsStream(Constants.FILE_CONFIG)) {
            String content = IOUtils.readStream(in);
            parseConfig(content);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GameException(e.getMessage(), e);
        }
    }

    private void parseConfig(String content) {
        Document document;

        try {
            document = DocumentHelper.parseText(content);
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
            throw new GameException(e.getMessage(), e);
        }

        Element configuration = document.getRootElement();
        parseWindow(configuration.element("window"));
        parseGame(configuration.element("game"));
        parsePanels(configuration.element("panels"));
        parseScore(configuration.element("score"));
    }

    private void parseWindow(Element window) {
        this.window = WindowConfig.builder()
                .title(window.attributeValue("title"))
                .titleHeight(Integer.parseInt(window.attributeValue("titleHeight")))
                .width(Integer.parseInt(window.attributeValue("width")))
                .height(Integer.parseInt(window.attributeValue("height")))
                .build();
    }

    private void parseGame(Element game) {
        this.game = GameConfig.builder()
                .width(Integer.parseInt(game.attributeValue("width")))
                .height(Integer.parseInt(game.attributeValue("height")))
                .level(Integer.parseInt(game.attributeValue("level")))
                .build();
    }

    private void parsePanels(Element panels) {
        Map<String, PanelConfig> panelConfigs = new HashMap<>();

        List<Element> panel = panels.elements("panel");
        for (Element element : panel) {
            String name = element.attributeValue("name");
            panelConfigs.put(name,
                    PanelConfig.builder()
                            .name(name)
                            .x(Integer.parseInt(element.attributeValue("x")))
                            .y(Integer.parseInt(element.attributeValue("y")))
                            .width(Integer.parseInt(element.attributeValue("width")))
                            .height(Integer.parseInt(element.attributeValue("height")))
                            .build());
        }

        this.panels = panelConfigs;
        this.border = Integer.parseInt(panels.attributeValue("border"));
        this.padding = Integer.parseInt(panels.attributeValue("padding"));
    }

    private void parseScore(Element score) {
        Map<Integer, Integer> map = new HashMap<>(16);

        List<Element> properties = score.elements("property");
        for (Element property : properties) {
            map.put(Integer.valueOf(property.attributeValue("key")),
                    Integer.valueOf(property.attributeValue("value")));
        }

        this.score = map;
    }

    /**
     * 获取某个面板配置
     *
     * @param name 面板名称
     * @return 面板配置
     */
    public PanelConfig getPanel(String name) {
        return panels.get(name);
    }
}
