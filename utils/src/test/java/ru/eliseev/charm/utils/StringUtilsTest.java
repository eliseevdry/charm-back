package ru.eliseev.charm.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("fast")
class StringUtilsTest {

    @Nested
    @DisplayName("Проверка валидности email")
    class ValidEmailTests {
        @Test
        @DisplayName("Когда email правильный - проверка проходит")
        void isValidEmail1() {
            // given
            String email = "user@email.ru";

            // when
            boolean result = StringUtils.isValidEmail(email);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Когда email не правильный - проверка не проходит")
        void isValidEmail2() {
            // given
            String email = "user...email";

            // when
            boolean result = StringUtils.isValidEmail(email);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Когда email пустой - проверка не проходит")
        void isValidEmail3() {
            // given
            String email = "";

            // when
            boolean result = StringUtils.isValidEmail(email);

            // then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Проверка на пустоту строки")
    class IsBlankTests {
        @Test
        @DisplayName("Когда строка пустая - проверка проходит")
        void isBlank1() {
            // given
            String str = "";

            // when
            boolean result = StringUtils.isBlank(str);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Когда строка не пустая - проверка не проходит")
        void isBlank2() {
            // given
            String str = "not blank";

            // when
            boolean result = StringUtils.isBlank(str);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Когда строка null - проверка проходит")
        void isBlank3() {
            // given
            String str = null;

            // when
            boolean result = StringUtils.isBlank(str);

            // then
            assertThat(result).isTrue();
        }
    }
}
