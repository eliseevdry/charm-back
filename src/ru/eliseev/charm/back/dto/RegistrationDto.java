package ru.eliseev.charm.back.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
}
