package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharmDto {
    Long fromProfile;
    Long toProfile;
    Action action;
}
