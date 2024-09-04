package ru.eliseev.charm.back.service;

import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.mapper.ProfileGetDtoMapper;
import ru.eliseev.charm.back.mapper.ProfileMapper;
import ru.eliseev.charm.back.model.Profile;

import java.util.List;
import java.util.Optional;

public class ProfileService {

    private static final ProfileService INSTANCE = new ProfileService();

    private final ProfileDao dao = ProfileDao.getInstance();

    private final ProfileGetDtoMapper profileGetDtoMapper = ProfileGetDtoMapper.getInstance();

    private final ProfileMapper profileMapper = ProfileMapper.getInstance();

    private ProfileService() {
    }

    public static ProfileService getInstance() {
        return INSTANCE;
    }

    public Long save(ProfileSaveDto dto) {
        return dao.save(profileMapper.map(dto, new Profile())).getId();
    }

    public Optional<ProfileGetDto> findById(Long id) {
        return dao.findById(id).map(profile -> profileGetDtoMapper.map(profile, new ProfileGetDto()));
    }

    public List<ProfileGetDto> findAll() {
        return dao.findAll().stream().map(profile -> profileGetDtoMapper.map(profile, new ProfileGetDto())).toList();
    }

    public void update(ProfileSaveDto dto) {
        dao.findById(dto.getId())
                .ifPresent(profile -> dao.update(profileMapper.map(dto, profile)));
    }

    public boolean delete(Long id) {
        return dao.delete(id);
    }
}
