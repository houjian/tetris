package io.github.houjian.tetris.core;

import io.github.houjian.tetris.config.Constants;
import io.github.houjian.tetris.model.Color;
import io.github.houjian.tetris.model.Skin;
import io.github.houjian.tetris.model.Type;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 图片管理
 *
 * @author houjian
 */
@Slf4j
public class ImageManager {

    private static Skin currentSkin;

    public static Image level;
    public static Image score;
    public static Image rmline;
    public static Image exp;
    public static Image record;
    public static Image number;

    public static Image start;
    public static Image pause;
    public static Image continued;

    public static Image border;
    public static Image layer;
    public static Image block;
    public static Image shadow;

    public static Image trough;
    public static Image rect;

    private static java.util.List<Image> backgrounds;
    private static Map<Type, Image> types;

    static {
        setSkin(Skin.DEFAULT);
    }

    /**
     * 重新加载图片
     */
    public static void setSkin(Skin skin) {
        if (skin == null || skin == currentSkin) {
            return;
        }

        final String skinName = skin.getName();
        log.info("start to load skin: name={}", skinName);

        currentSkin = skin;

        level = loadImage("string/level.png");
        score = loadImage("string/score.png");
        rmline = loadImage("string/rmline.png");
        exp = loadImage("string/exp.png");
        record = loadImage("string/record.png");
        number = loadImage("string/number.png");

        start = loadImage("string/start.png");
        pause = loadImage("string/pause.png");
        continued = loadImage("string/continue.png");

        border = loadImage("game/border.png");
        layer = loadImage("game/layer.png");
        block = loadImage("game/block.png");
        shadow = loadImage("game/shadow.png");

        trough = loadImage("window/trough.png");
        rect = loadImage("window/rect.png");

        backgrounds = loadBackground();
        types = loadType(block);
    }

    /**
     * 加载背景图片
     *
     * @return 背景图片列表
     */
    private static java.util.List<Image> loadBackground() {
        final File[] files = new File(Constants.DIR_RESOURCE, "background").listFiles(ImageManager::isImage);
        if (files != null) {
            final List<Image> imageList = Arrays.stream(files)
                    .map(ImageManager::loadImage)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            log.info("load background image, size={}", imageList.size());

            return imageList;
        }
        return Collections.emptyList();
    }

    private static boolean isImage(File file) {
        return file.isFile()
                && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png"));
    }

    /**
     * 加载方块类型图片
     *
     * @param block 方块图片
     * @return 方块类型
     */
    private static Map<Type, Image> loadType(Image block) {
        int width = block.getWidth(null) / Color.values().length;
        int height = block.getHeight(null);

        return Arrays.stream(Type.values())
                .collect(Collectors.toMap(Function.identity(), type -> {
                    int index = type.getColor().getIndex();

                    int sizeX = Arrays.stream(type.getPoints()).mapToInt(p -> p.x).max().orElse(0) + 1;
                    int sizeY = Arrays.stream(type.getPoints()).mapToInt(p -> p.y).max().orElse(0) + 1;

                    BufferedImage image = new BufferedImage(sizeY * width, sizeX * height, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics graphics = image.getGraphics();

                    for (Point point : type.getPoints()) {
                        graphics.drawImage(block,
                                point.y * width, point.x * height, (point.y + 1) * width, (point.x + 1) * height,
                                index * width, 0, (index + 1) * width, height,
                                null);
                    }

                    return image;
                }, (k1, k2) -> k1, () -> new EnumMap<>(Type.class)));
    }

    /**
     * 获取一张方块图片
     */
    public static Image getType(Type type) {
        return types.get(type);
    }

    /**
     * 获取一张背景图片
     */
    public static Image getBackground(int index) {
        return backgrounds.get(index % backgrounds.size());
    }

    /**
     * 根据图片路径加载图片文件
     *
     * @param resource resource下的资源文件
     * @return 图片对象
     */
    public static Image loadImage(String resource) {
        return loadImage(new File(Constants.DIR_RESOURCE, resource));
    }

    /**
     * 加载图片文件
     *
     * @param file 图片文件
     * @return 图片对象
     */
    public static Image loadImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            log.error("load image fail: {}", file.getAbsolutePath(), e);
        }
        return null;
    }

    /**
     * 根据图片大小和需求大小计算图片缩放后宽高
     *
     * @param imgWidth  图片宽度
     * @param imgHeight 图片高度
     * @param reqWidth  需求高度
     * @param reqHeight 需求宽度
     * @return 图片等比例缩放后的宽高
     */
    public static Dimension calculateSample(int imgWidth, int imgHeight, int reqWidth, int reqHeight) {
        double sample = 1.0;

        if (imgWidth > reqWidth || imgHeight > reqHeight) {
            double sampleW = (double) imgWidth / (double) reqWidth;
            double sampleH = (double) imgHeight / (double) reqHeight;
            sample = Math.max(sampleW, sampleH);
        }

        return new Dimension((int) (imgWidth / sample), (int) (imgHeight / sample));
    }

    /**
     * 等比例拉伸图片
     *
     * @param image 图片
     * @param scale 拉伸比例
     * @return 缩放后的图片
     */
    public static Image scaledImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);

        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
}
