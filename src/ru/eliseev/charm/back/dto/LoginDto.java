package ru.eliseev.charm.back.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
