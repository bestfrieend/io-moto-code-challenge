package com.iomoto.challenge.utils;

import org.springframework.util.StringUtils;

public class StringUtil {

    public static boolean isBlank(String s) {
        int strLen;
        if (s == null || (strLen = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(s.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
