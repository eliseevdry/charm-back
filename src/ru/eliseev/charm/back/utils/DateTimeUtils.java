package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtils {

    public static int getAge(LocalDate birthDate) {
        return Math.toIntExact(ChronoUnit.YEARS.between(birthDate, LocalDate.now()));
    }

    public static boolean isValidAge(LocalDate birthDate) {
        if (birthDate == null) return false;
        int age = getAge(birthDate);
        return age > 18 && age < 100;
    }
}
