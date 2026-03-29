package ru.eliseev.charm.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            assertTrue(result, "Ошибка в проверке 1");
        }

        @Test
        @DisplayName("Когда email не правильный - проверка не проходит")
        void isValidEmail2() {
            // given
            String email = "user...email";

            // when
            boolean result = StringUtils.isValidEmail(email);

            // then
            assertFalse(result, "Ошибка: в проверке 2");
        }

        @Test
        @DisplayName("Когда email пустой - проверка не проходит")
        void isValidEmail3() {
            // given
            String email = "";

            // when
            boolean result = StringUtils.isValidEmail(email);

            // then
            assertFalse(result, "Ошибка: в проверке 3");
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
            assertTrue(result, "Ошибка: строка не была признана пустой");
        }

        @Test
        @DisplayName("Когда строка не пустая - проверка не проходит")
        void isBlank2() {
            // given
            String str = "not blank";

            // when
            boolean result = StringUtils.isBlank(str);

            // then
            assertFalse(result, "Ошибка: строка была признана пустой");
        }

        @Test
        @DisplayName("Когда строка null - проверка проходит")
        void isBlank3() {
            // given
            String str = null;

            // when
            boolean result = StringUtils.isBlank(str);

            // then
            assertTrue(result, "Ошибка: null не был признан пустым");
        }
    }
}
