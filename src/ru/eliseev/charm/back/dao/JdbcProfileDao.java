package ru.eliseev.charm.back.dao;

import ru.eliseev.charm.back.model.Profile;

import java.util.List;
import java.util.Optional;

public class JdbcProfileDao implements ProfileDao {

    private static final ProfileDao INSTANCE = new JdbcProfileDao();

    public static ProfileDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findByEmailAndPassword(String email, String password) {
        return Optional.empty();
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public void update(Profile profile) {

    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
