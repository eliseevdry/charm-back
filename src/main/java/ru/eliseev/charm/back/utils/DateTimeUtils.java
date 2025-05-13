package ru.eliseev.charm.back.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtils {

    public static int getAge(LocalDate birthDate) {
        return Math.toIntExact(ChronoUnit.YEARS.between(birthDate, LocalDate.now()));
    }

    public static Date getPastDate(int age) {
        return Date.valueOf(LocalDate.now().minusYears(age));
    }

    public static boolean isValidAge(LocalDate birthDate) {
        if (birthDate == null) return false;
        int age = getAge(birthDate);
        return age > 18 && age < 100;
    }
}
