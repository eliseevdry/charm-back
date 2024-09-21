package ru.eliseev.charm.back.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class Profile {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String about;
    private Gender gender;
    private Status status;
}
