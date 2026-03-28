package ru.eliseev.charm.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UrlUtilsTest {
    @Test
    void whenPasswordIsExistThenHashIsDifferent() {
        String password = "qwerty";
        assertNotEquals(password, PasswordUtils.hashPassword(password), "Ошибка в проверке 1");
    }
}
