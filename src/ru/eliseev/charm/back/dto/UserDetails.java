package ru.eliseev.charm.back.dto;

import lombok.Data;
import ru.eliseev.charm.back.model.Role;

@Data
public class UserDetails {
    private Long id;
    private String email;
    private Role role;
}
