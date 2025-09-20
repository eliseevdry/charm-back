package ru.eliseev.charm.back.utils;

import junit.framework.TestCase;

public class UrlUtilsTest extends TestCase {
    public void test() {
        String password = "qwerty";
        assert !PasswordUtils.hashPassword(password).equals(password) : "Ошибка в проверке 1";
    }
}
