package ru.eliseev.charm.back.test;

import ru.eliseev.charm.back.utils.PasswordUtils;

public class UrlUtilsTest {
    public static void main(String[] args) {
        String password = "qwerty";
        assert !PasswordUtils.hashPassword(password).equals(password) : "Ошибка в проверке 1";
    }
}
