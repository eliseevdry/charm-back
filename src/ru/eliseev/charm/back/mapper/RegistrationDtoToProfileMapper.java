package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.model.Role;
import ru.eliseev.charm.back.model.Status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationDtoToProfileMapper implements Mapper<RegistrationDto, Profile> {

    private static final RegistrationDtoToProfileMapper INSTANCE = new RegistrationDtoToProfileMapper();

    public static RegistrationDtoToProfileMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile map(RegistrationDto dto) {
        return map(dto, new Profile());
    }

    @Override
    public Profile map(RegistrationDto dto, Profile profile) {
        profile.setEmail(dto.getEmail());
        profile.setPassword(dto.getPassword());
        profile.setStatus(Status.INACTIVE);
        profile.setRole(Role.USER);
        return profile;
    }
}
