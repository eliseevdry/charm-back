package ru.eliseev.charm.back.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.eliseev.charm.back.model.Status;

import static ru.eliseev.charm.back.utils.ConnectionManager.DEFAULT_PAGE;
import static ru.eliseev.charm.back.utils.ConnectionManager.DEFAULT_PAGE_SIZE;
import static ru.eliseev.charm.back.utils.ConnectionManager.DEFAULT_SORTED_COLUMN;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileFilter {
    String emailStartWith;
    String nameStartWith;
    String surnameStartWith;
    Integer ltAge;
    Integer gteAge;
    Status status;
    String sort = DEFAULT_SORTED_COLUMN;
    Integer page = DEFAULT_PAGE;
    Integer pageSize = DEFAULT_PAGE_SIZE;
}
