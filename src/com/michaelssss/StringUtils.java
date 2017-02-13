package com.michaelssss;

/**
 * Created by michaelssss on 2017/2/6.
 */
public class StringUtils {
    public static boolean isBlank(String s) {
        return null != s && "".equals(s.trim());
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
