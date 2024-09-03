package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.model.Profile;

public class ProfileMapper implements Mapper<ProfileSaveDto, Profile> {

    private static final ProfileMapper INSTANCE = new ProfileMapper();

    private ProfileMapper() {
    }

    public static ProfileMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile map(ProfileSaveDto dto, Profile profile) {
        Profile result = profile;
        
        if (result == null) {
            result = new Profile();
        }
        
        if (dto.getId() == null) {
            result.setEmail(dto.getEmail());
            result.setPassword(dto.getPassword());
        } else {
            result.setId(dto.getId());

            if (dto.getEmail() != null) {
                result.setEmail(dto.getEmail());
            }

            if (dto.getPassword() != null) {
                result.setPassword(dto.getPassword());
            }
            
            if (dto.getName() != null) {
                result.setName(dto.getName());
            }

            if (dto.getSurname() != null) {
                result.setSurname(dto.getSurname());
            }
            
            if (dto.getBirthDate() != null) {
                result.setBirthDate(dto.getBirthDate());
            }
            
            if (dto.getAbout() != null) {
                result.setAbout(dto.getAbout());
            }
            
            if (dto.getGender() != null) {
                result.setGender(dto.getGender());
            }

            if (dto.getStatus() != null) {
                result.setStatus(dto.getStatus());
            }
        }
        return result;
    }
}
