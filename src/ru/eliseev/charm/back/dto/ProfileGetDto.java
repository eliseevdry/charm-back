package ru.eliseev.charm.back.dto;

import lombok.Data;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.time.LocalDate;

@Data
public class ProfileGetDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Integer age;
    private String about;
    private Gender gender;
    private Status status;
    private String photo;
}
