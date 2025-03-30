package ru.eliseev.charm.back.service;

import static ru.eliseev.charm.back.utils.UrlUtils.getProfilePhotoPath;

import jakarta.servlet.http.Part;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.dto.LoginDto;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.dto.ProfileFullUpdateDto;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.dto.ProfileUpdateStatusDto;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.CredentialsDtoToProfileMapper;
import ru.eliseev.charm.back.mapper.ProfileFullUpdateDtoToProfileMapper;
import ru.eliseev.charm.back.mapper.ProfileToProfileGetDtoMapper;
import ru.eliseev.charm.back.mapper.ProfileToUserDetailsMapper;
import ru.eliseev.charm.back.mapper.ProfileUpdateDtoToProfileMapper;
import ru.eliseev.charm.back.model.Profile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileService {

    private static final ProfileService INSTANCE = new ProfileService();

	private final ProfileDao dao = ProfileDao.getInstance();

    private final ContentService contentService = ContentService.getInstance();

    private final ProfileToProfileGetDtoMapper profileToProfileGetDtoMapper = ProfileToProfileGetDtoMapper.getInstance();

    private final ProfileToUserDetailsMapper profileToUserDetailsMapper = ProfileToUserDetailsMapper.getInstance();

    private final ProfileUpdateDtoToProfileMapper profileUpdateDtoToProfileMapper = ProfileUpdateDtoToProfileMapper.getInstance();

    private final ProfileFullUpdateDtoToProfileMapper profileFullUpdateDtoToProfileMapper = ProfileFullUpdateDtoToProfileMapper.getInstance();

    private final CredentialsDtoToProfileMapper credentialsDtoToProfileMapper = CredentialsDtoToProfileMapper.getInstance();

    public static ProfileService getInstance() {
        return INSTANCE;
    }

    public Long save(RegistrationDto dto) {
        return dao.save(dto.getEmail(), dto.getPassword());
    }

    public Optional<ProfileGetDto> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return dao.findById(id).map(profileToProfileGetDtoMapper::map);
    }

	public List<ProfileGetDto> findMatches(Long id, ProfileFilter filter) {
		if (id == null) {
			return List.of();
		}
		return dao.findMatches(id, filter).stream().map(profileToProfileGetDtoMapper::map).toList();
	}

    public Optional<UserDetails> login(LoginDto dto) {
        return dao.findByEmailAndPassword(dto.getEmail(), dto.getPassword()).map(profileToUserDetailsMapper::map);
    }

    public List<ProfileGetDto> findAll(ProfileFilter filter) {
        return dao.findAll(filter).stream().map(profileToProfileGetDtoMapper::map).toList();
    }

    @SneakyThrows
    public void update(ProfileUpdateDto dto) {
        Optional<Profile> optProfile = dao.findById(dto.getId());
        if (optProfile.isPresent()) {
            Part photo = dto.getPhoto();
            if (photo != null) {
                contentService.upload(
                        getProfilePhotoPath(dto.getId(), photo.getSubmittedFileName()),
                        photo.getInputStream()
                );
            }
            dao.update(profileUpdateDtoToProfileMapper.map(dto, optProfile.get()));
        }
    }

    @SneakyThrows
    public void updateStatuses(List<ProfileUpdateStatusDto> dtos) {
        if (dtos.isEmpty()) {
            return;
        }
        dao.updateStatuses(dtos);
    }

    @SneakyThrows
    public void update(ProfileFullUpdateDto dto) {
        Optional<Profile> optProfile = dao.findById(dto.getId());
        if (optProfile.isPresent()) {
            Part photo = dto.getPhoto();
            if (photo != null) {
                contentService.upload(
                        getProfilePhotoPath(dto.getId(), photo.getSubmittedFileName()),
                        photo.getInputStream()
                );
            }
            dao.update(profileFullUpdateDtoToProfileMapper.map(dto, optProfile.get()));
        }
    }

    public void update(CredentialsDto dto) {
        dao.findById(dto.getId()).ifPresent(profile -> dao.update(credentialsDtoToProfileMapper.map(dto, profile)));
    }

    public boolean delete(Long id) {
        return dao.delete(id);
    }
}
