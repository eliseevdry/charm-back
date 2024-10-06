package ru.eliseev.charm.back.dto;

import lombok.Data;

@Data
public class CredentialsDto {
    private Long id;
    private String email;
    private String newPassword;
    private String confirmNewPassword;
    private String currentPassword;
}
