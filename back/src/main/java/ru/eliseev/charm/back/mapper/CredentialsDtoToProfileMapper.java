package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.utils.PasswordUtils;

import static ru.eliseev.charm.utils.StringUtils.isBlank;

public class CredentialsDtoToProfileMapper implements Mapper<CredentialsDto, Profile> {

    @Override
    public Profile map(CredentialsDto dto) {
        return map(dto, new Profile());
    }

    @Override
    public Profile map(CredentialsDto dto, Profile profile) {
        profile.setId(dto.getId());
        profile.setVersion(dto.getVersion());
        if (!isBlank(dto.getEmail())) {
            profile.setEmail(dto.getEmail());
        }
        if (!isBlank(dto.getNewPassword())) {
            String passwordHash = PasswordUtils.hashPassword(dto.getNewPassword());
            profile.setPassword(passwordHash);
        }
        return profile;
    }
}
