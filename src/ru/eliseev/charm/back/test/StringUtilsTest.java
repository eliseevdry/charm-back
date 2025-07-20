package ru.eliseev.charm.back.test;

import ru.eliseev.charm.back.utils.StringUtils;

public class StringUtilsTest {
    public static void main(String[] args) {
        assert StringUtils.isValidEmail("user@email.ru") : "Ошибка в проверке 1";
        assert !StringUtils.isValidEmail("user...email") : "Ошибка: в проверке 2";
        assert !StringUtils.isValidEmail("") : "Ошибка: в проверке 3";
    }
}
