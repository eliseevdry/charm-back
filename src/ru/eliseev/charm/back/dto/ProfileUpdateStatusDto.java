package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Status;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileUpdateStatusDto {
    Long id;
    Status status;
}
