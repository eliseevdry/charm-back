package ru.eliseev.charm.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class PasswordUtilsTest {
    @Test
    @DisplayName("Когда пароль захеширован за 100 мс и он не должен быть равен исходному")
    void hashPassword1() {
        // given
        String password = "qwerty";

        // when
        String result = assertDoesNotThrow(() -> assertTimeout(Duration.ofMillis(100), () -> PasswordUtils.hashPassword(password)));

        // then
        assertThat(result)
            .isNotNull()
            .isNotEqualTo(password)
            .hasSizeGreaterThan(password.length());
    }

    @Test
    @DisplayName("Когда пароль null - он должен выбрасывать исключение")
    void hashPassword2() {
        // given
        String password = null;

        // when
        NullPointerException exception = assertThrows(NullPointerException.class, () -> PasswordUtils.hashPassword(password));

        // then
        assertThat(exception)
            .isNotNull()
            .satisfies(
                ex -> assertThat(ex.getMessage()).isNotNull(),
                ex -> assertThat(ex.getMessage()).isEqualTo("Must not be null")
            );
    }
}
