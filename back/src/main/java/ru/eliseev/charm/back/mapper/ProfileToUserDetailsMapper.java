package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.model.Profile;

public class ProfileToUserDetailsMapper implements Mapper<Profile, UserDetails> {

    @Override
    public UserDetails map(Profile profile) {
        return map(profile, new UserDetails());
    }

    @Override
    public UserDetails map(Profile profile, UserDetails dto) {
        dto.setId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setRole(profile.getRole());
        return dto;
    }
}
