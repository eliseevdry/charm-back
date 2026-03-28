package ru.eliseev.charm.utils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    @Tag("fast")
    void checkEmails() {
        assertTrue(StringUtils.isValidEmail("user@email.ru"), "Ошибка в проверке 1");
        assertFalse(StringUtils.isValidEmail("user...email"), "Ошибка: в проверке 2");
        assertFalse(StringUtils.isValidEmail(""), "Ошибка: в проверке 3");
    }
}
