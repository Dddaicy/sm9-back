package com.sm9.boot.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }

    public static boolean isNullOrEmpty(String[] strings) {
        return strings.length == 0;
    }
}
