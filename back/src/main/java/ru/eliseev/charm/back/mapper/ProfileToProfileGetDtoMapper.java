package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.model.Profile;

import static ru.eliseev.charm.back.utils.UrlUtils.getProfilePhotoPath;
import static ru.eliseev.charm.utils.DateTimeUtils.getAge;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileToProfileGetDtoMapper implements Mapper<Profile, ProfileGetDto> {

    private static final ProfileToProfileGetDtoMapper INSTANCE = new ProfileToProfileGetDtoMapper();

    public static ProfileToProfileGetDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProfileGetDto map(Profile profile) {
        return map(profile, new ProfileGetDto());
    }

    @Override
    public ProfileGetDto map(Profile profile, ProfileGetDto dto) {
        dto.setId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setBirthDate(profile.getBirthDate());
        if (profile.getBirthDate() != null) {
            dto.setAge(getAge(profile.getBirthDate()));
        }
        dto.setAbout(profile.getAbout());
        dto.setGender(profile.getGender());
        dto.setStatus(profile.getStatus());
        if (profile.getPhoto() != null) {
            dto.setPhoto(getProfilePhotoPath(profile.getId(), profile.getPhoto()));
        }
        dto.setRole(profile.getRole());
        dto.setVersion(profile.getVersion());
        return dto;
    }
}
