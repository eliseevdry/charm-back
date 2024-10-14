package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.model.Profile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileToUserDetailsMapper implements Mapper<Profile, UserDetails> {

    private static final ProfileToUserDetailsMapper INSTANCE = new ProfileToUserDetailsMapper();

    public static ProfileToUserDetailsMapper getInstance() {
        return INSTANCE;
    }

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
