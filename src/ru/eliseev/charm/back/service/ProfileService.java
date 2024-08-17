package ru.eliseev.charm.back.service;

import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.model.Profile;

import java.util.List;
import java.util.Optional;

public class ProfileService {

    private static final ProfileService INSTANCE = new ProfileService();

    private final ProfileDao dao = ProfileDao.getInstance();

    private ProfileService() {
    }

    public static ProfileService getInstance() {
        return INSTANCE;
    }

    public Profile save(Profile profile) {
        return dao.save(profile);
    }

    public Optional<Profile> findById(Long id) {
        if (id == null) return Optional.empty();
        return dao.findById(id);
    }

    public List<Profile> findAll() {
        return dao.findAll();
    }

    public void update(Profile profile) {
        dao.update(profile);
    }

    public boolean delete(Long id) {
        if (id == null) return false;
        return dao.delete(id);
    }
}
