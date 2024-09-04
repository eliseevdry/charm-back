package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.model.Profile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProfileGetDtoMapper implements Mapper<Profile, ProfileGetDto> {

    private static final ProfileGetDtoMapper INSTANCE = new ProfileGetDtoMapper();

    private ProfileGetDtoMapper() {
    }

    public static ProfileGetDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProfileGetDto map(Profile profile, ProfileGetDto dto) {
        ProfileGetDto result = dto;
        if (result ==null) {
            result = new ProfileGetDto();
        }
        result.setId(profile.getId());
        result.setEmail(profile.getEmail());
        result.setName(profile.getName());
        result.setSurname(profile.getSurname());
        result.setBirthDate(profile.getBirthDate());
        if (profile.getBirthDate() != null) {
            result.setAge(Math.toIntExact(ChronoUnit.YEARS.between(profile.getBirthDate(), LocalDate.now())));
        }
        result.setAbout(profile.getAbout());
        result.setGender(profile.getGender());
        result.setStatus(profile.getStatus());
        return result;
    }
}
