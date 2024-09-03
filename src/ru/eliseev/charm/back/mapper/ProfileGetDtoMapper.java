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
    public ProfileGetDto map(Profile profile) {
        ProfileGetDto dto = new ProfileGetDto();
        dto.setId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setBirthDate(profile.getBirthDate());
        dto.setAge(Math.toIntExact(ChronoUnit.YEARS.between(profile.getBirthDate(), LocalDate.now())));
        dto.setAbout(profile.getAbout());
        dto.setGender(profile.getGender());
        return dto;
    }
}
