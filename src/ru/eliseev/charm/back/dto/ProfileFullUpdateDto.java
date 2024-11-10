package ru.eliseev.charm.back.dto;

import jakarta.servlet.http.Part;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileFullUpdateDto {
    Long id;
    String email;
    String password;
    String name;
    String surname;
    LocalDate birthDate;
    String about;
    Gender gender;
    Status status;
    Part photo;
}
