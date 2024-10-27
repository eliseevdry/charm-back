package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CredentialsDto {
    Long id;
    String email;
    String newPassword;
    String confirmNewPassword;
    String currentPassword;
}
