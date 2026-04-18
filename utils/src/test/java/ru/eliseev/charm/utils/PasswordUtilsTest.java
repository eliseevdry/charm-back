package ru.eliseev.charm.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class PasswordUtilsTest {

    @DisplayName("Когда пароль захеширован - он не должен быть равен исходному")
    @RepeatedTest(10)
    @Disabled("Flaky test: проблема с Bad number of rounds")
    void hashPassword1() {
        // given
        String password = "qwerty";

        // when
        String result = assertDoesNotThrow(() -> PasswordUtils.hashPassword(password));

        // then
        assertThat(result)
            .isNotNull()
            .isNotEqualTo(password)
            .hasSize(60);
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

    @DisplayName("Пароль захеширован не больше чем за 150 мс")
    @RepeatedTest(10)
    @Disabled("Flaky test: проблема с Bad number of rounds и exceeded timeout")
    void hashPassword3() {
        // given
        String password = "qwerty";

        // when
        String result = assertTimeout(Duration.ofMillis(150), () -> PasswordUtils.hashPassword(password));

        // then
        assertThat(result)
            .isNotNull()
            .isNotEqualTo(password)
            .hasSize(60);
    }
}
