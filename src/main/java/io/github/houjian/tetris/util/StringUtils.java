package io.github.houjian.tetris.util;

/**
 * 字符串工具类
 *
 * @author houjian
 */
public class StringUtils {

    /**
     * 判断字符串是否为空串
     *
     * @param cs 字符串
     * @return 为空串返回true
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符串是否不为空串
     *
     * @param cs 字符串
     * @return 不为空串返回true
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串是否为空白字符串
     *
     * @param cs 字符串
     * @return 为空白字符串返回true
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空白字符串
     *
     * @param cs 字符串
     * @return 不为空白字符串返回true
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 计算字符串长度
     *
     * @param cs 字符串
     * @return 字符串长度
     */
    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * 判断字符串是否相同
     *
     * @param cs1 字符串1
     * @param cs2 字符串2
     * @return 字符串相同返回true
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        final int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
