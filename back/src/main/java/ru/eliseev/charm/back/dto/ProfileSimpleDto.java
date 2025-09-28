package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileSimpleDto {
    Long id;
    String name;
    String surname;
    Integer age;
    String about;
    String photo;
}
