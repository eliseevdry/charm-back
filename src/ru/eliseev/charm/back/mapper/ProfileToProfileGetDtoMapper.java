package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.model.Profile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
            dto.setAge(Math.toIntExact(ChronoUnit.YEARS.between(profile.getBirthDate(), LocalDate.now())));
        }
        dto.setAbout(profile.getAbout());
        dto.setGender(profile.getGender());
        dto.setStatus(profile.getStatus());
        dto.setPhoto(profile.getPhoto());
        return dto;
    }
}
