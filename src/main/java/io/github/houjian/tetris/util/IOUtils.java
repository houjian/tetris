package io.github.houjian.tetris.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * IO工具类
 *
 * @author houjian
 */
@Slf4j
public class IOUtils {

    /**
     * 向文件中写入对象
     *
     * @param object   待写入对象
     * @param filename 文件路径
     * @return 写入成功返回true
     */
    public static boolean writeObject(Object object, String filename) {
        ensureDirectoryExists(filename);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(object);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 确保路径所在目录存在
     *
     * @param filename 文件路径
     */
    public static void ensureDirectoryExists(String filename) {
        File dir = new File(filename).getParentFile();
        if (dir.exists()) {
            return;
        }

        log.warn("file is not exists: {}", dir.getAbsolutePath());

        if (dir.mkdir()) {
            log.debug("mkdir {}", dir.getAbsolutePath());
        } else {
            log.error("mkdir error: {}", dir.getAbsolutePath());
        }
    }

    /**
     * 读取文件中的对象
     *
     * @param filename 文件路径
     * @return 读取的对象
     */
    public static Object readObject(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            log.warn("file is not exists: {}", file.getAbsolutePath());
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 读取文件内容
     *
     * @param filepath 文件路径
     * @return 文件内容
     */
    public static String readFile(String filepath) {
        return readFile(new File(filepath));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return 文件内容
     */
    public static String readFile(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return readStream(in);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 从流读取文件内容
     *
     * @param in 输入流
     * @return 文件内容
     */
    public static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        int len;
        char[] buffer = new char[4096];

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            while ((len = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 写入文件
     *
     * @param filepath 文件路径
     * @param content  文件内容
     */
    public static boolean writeFile(String filepath, String content) {
        ensureDirectoryExists(filepath);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filepath), StandardCharsets.UTF_8))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
