package ru.eliseev.charm.back.dao;

import ru.eliseev.charm.back.model.Profile;

import java.util.Optional;

public interface ProfileDao extends Dao<Long, Profile> {

    Optional<Profile> findByEmailAndPassword(String email, String password);

    boolean existByEmail(String email);
}
