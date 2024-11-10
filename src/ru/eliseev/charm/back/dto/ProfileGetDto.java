package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Role;
import ru.eliseev.charm.back.model.Status;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileGetDto {
    Long id;
    String email;
    String name;
    String surname;
    LocalDate birthDate;
    Integer age;
    String about;
    Gender gender;
    Status status;
    String photo;
    Role role;
}
