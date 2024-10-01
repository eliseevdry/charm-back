package ru.eliseev.charm.back.service;

import jakarta.servlet.http.Part;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.mapper.ProfileToProfileGetDtoMapper;
import ru.eliseev.charm.back.mapper.ProfileUpdateDtoToProfileMapper;
import ru.eliseev.charm.back.mapper.RegistrationDtoToProfileMapper;
import ru.eliseev.charm.back.model.Profile;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileService {

    private static final ProfileService INSTANCE = new ProfileService();

    private final ProfileDao dao = ProfileDao.getInstance();
    
    private final ContentService contentService = ContentService.getInstance();

    private final ProfileToProfileGetDtoMapper profileToProfileGetDtoMapper = ProfileToProfileGetDtoMapper.getInstance();

    private final ProfileUpdateDtoToProfileMapper profileUpdateDtoToProfileMapper = ProfileUpdateDtoToProfileMapper.getInstance();

    private final RegistrationDtoToProfileMapper registrationDtoToProfileMapper = RegistrationDtoToProfileMapper.getInstance();

    public static ProfileService getInstance() {
        return INSTANCE;
    }

    public Long save(RegistrationDto dto) {
        return dao.save(registrationDtoToProfileMapper.map(dto)).getId();
    }

    public Optional<ProfileGetDto> findById(Long id) {
        return dao.findById(id).map(profileToProfileGetDtoMapper::map);
    }

    public List<ProfileGetDto> findAll() {
        return dao.findAll().stream().map(profileToProfileGetDtoMapper::map).toList();
    }

    @SneakyThrows
    public void update(ProfileUpdateDto dto) {
        Optional<Profile> optProfile = dao.findById(dto.getId());
        if (optProfile.isPresent()) {
            Part photo = dto.getPhoto();
            contentService.upload("/profile/" + dto.getId() + "/" + photo.getSubmittedFileName(), photo.getInputStream());
            dao.update(profileUpdateDtoToProfileMapper.map(dto, optProfile.get()));
        }
    }

    public boolean delete(Long id) {
        return dao.delete(id);
    }
}
