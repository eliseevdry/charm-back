package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CredentialsDto {
    Long id;
    int version = 0;
    String email;
    String newPassword;
    String confirmNewPassword;
    String currentPassword;
}
