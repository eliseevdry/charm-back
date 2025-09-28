package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Role;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetails {
    Long id;
    String email;
    Role role;
}
