package ru.eliseev.charm.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordUtilsTest {
    @Test
    @DisplayName("Когда пароль захеширован - он не должен быть равен исходному")
    void hashPassword1() {
        // given
        String password = "qwerty";

        // when
        String result = PasswordUtils.hashPassword(password);

        // then
        assertNotEquals(password, result, "Ошибка: пароль не был захеширован");
    }
}
