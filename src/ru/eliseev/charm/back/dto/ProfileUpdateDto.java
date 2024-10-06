package ru.eliseev.charm.back.dto;

import jakarta.servlet.http.Part;
import lombok.Data;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.time.LocalDate;

@Data
public class ProfileUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String about;
    private Gender gender;
    private Status status;
    private Part photo;
}
