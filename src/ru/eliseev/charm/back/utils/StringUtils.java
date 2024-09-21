package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
