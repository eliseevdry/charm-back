package ru.eliseev.charm.back.test;

import ru.eliseev.charm.back.utils.StringUtils;

public class StringUtilsTest {
    public static void main(String[] args) {
        assert StringUtils.isValidEmail("user@email.ru");
        assert !StringUtils.isValidEmail("user...email");
        assert !StringUtils.isValidEmail("");
    }
}
