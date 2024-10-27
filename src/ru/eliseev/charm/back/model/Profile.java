package ru.eliseev.charm.back.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {
    Long id;
    String email;
    String password;
    String name;
    String surname;
    LocalDate birthDate;
    String about;
    Gender gender;
    String photo;
    Status status;
    Role role;
}
