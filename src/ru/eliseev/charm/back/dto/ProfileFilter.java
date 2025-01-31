package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Status;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileFilter {
    String emailStartWith;
    String nameStartWith;
    String surnameStartWith;
    Integer ltAge;
    Integer gteAge;
    Status status;
}
