package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.model.Profile;

public class ProfileUpdateDtoToProfileMapper implements Mapper<ProfileUpdateDto, Profile> {

    private static final ProfileUpdateDtoToProfileMapper INSTANCE = new ProfileUpdateDtoToProfileMapper();

    private ProfileUpdateDtoToProfileMapper() {
    }

    public static ProfileUpdateDtoToProfileMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile map(ProfileUpdateDto dto) {
        return map(dto, new Profile());
    }

    @Override
    public Profile map(ProfileUpdateDto dto, Profile profile) {
        profile.setId(dto.getId());
        if (dto.getEmail() != null) {
            profile.setEmail(dto.getEmail());
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
        return profile;
    }
}
