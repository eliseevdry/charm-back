package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Profile;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class ProfileMapper implements Mapper<ProfileSaveDto, Profile> {

    private static final ProfileMapper INSTANCE = new ProfileMapper();

    private ProfileMapper() {
    }

    public static ProfileMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public Profile map(ProfileSaveDto dto) {
        Profile profile = new Profile();
        profile.setId(dto.getId());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setBirthDate(dto.getBirthDate());
        profile.setAbout(dto.getAbout());
        profile.setGender(dto.getGender());
        return profile;
    }
}
