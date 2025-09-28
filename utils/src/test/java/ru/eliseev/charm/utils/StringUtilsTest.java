package ru.eliseev.charm.utils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {
    public void test() {
        assert StringUtils.isValidEmail("user@email.ru") : "Ошибка в проверке 1";
        assert !StringUtils.isValidEmail("user...email") : "Ошибка: в проверке 2";
        assert !StringUtils.isValidEmail("") : "Ошибка: в проверке 3";
    }
}
