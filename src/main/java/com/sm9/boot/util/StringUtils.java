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

    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return 首字母大写后的原字符串
     */
    public static String capitalize(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
