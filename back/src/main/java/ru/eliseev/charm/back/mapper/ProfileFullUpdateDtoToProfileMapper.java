package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.ProfileFullUpdateDto;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.utils.PasswordUtils;

public class ProfileFullUpdateDtoToProfileMapper implements Mapper<ProfileFullUpdateDto, Profile> {

    @Override
    public Profile map(ProfileFullUpdateDto dto) {
        return map(dto, new Profile());
    }

    @Override
    public Profile map(ProfileFullUpdateDto dto, Profile profile) {
        profile.setId(dto.getId());
        profile.setVersion(dto.getVersion());
        if (dto.getEmail() != null) {
            profile.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            String passwordHash = PasswordUtils.hashPassword(dto.getPassword());
            profile.setPassword(passwordHash);
        }
        if (dto.getName() != null) {
            profile.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            profile.setSurname(dto.getSurname());
        }
        if (dto.getBirthDate() != null) {
            profile.setBirthDate(dto.getBirthDate());
        }
        if (dto.getAbout() != null) {
            profile.setAbout(dto.getAbout());
        }
        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
        }
        if (dto.getStatus() != null) {
            profile.setStatus(dto.getStatus());
        }
        if (dto.getPhoto() != null) {
            profile.setPhoto(dto.getPhoto().getSubmittedFileName());
        }
        return profile;
    }
}
