package ru.eliseev.charm.back.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<ID, T> {
    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity);

    boolean delete(ID id);
}
