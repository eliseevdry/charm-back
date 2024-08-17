package ru.eliseev.charm.back.dao;

import ru.eliseev.charm.back.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProfileDao {
    
    private static final ProfileDao INSTANCE = new ProfileDao();

    private final AtomicLong idStorage;
    private final ConcurrentHashMap<Long, Profile> storage;

    private ProfileDao() {
        this.storage = new ConcurrentHashMap<>();
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setEmail("ivanov@mail.ru");
        profile.setName("Ivan");
        profile.setSurname("Ivanov");
        profile.setAbout("I am QA");
        this.storage.put(1L, profile);
        Profile profile1 = new Profile();
        profile1.setId(2L);
        profile1.setEmail("sidorova@mail.ru");
        profile1.setName("Elena");
        profile1.setSurname("Sidorova");
        profile1.setAbout("I am Java Dev");
        this.storage.put(2L, profile1);
        this.idStorage = new AtomicLong(1L);
    }

    public static ProfileDao getInstance() {
        return INSTANCE;
    }

    public Profile save(Profile profile) {
        profile.setId(idStorage.getAndIncrement());
        storage.put(profile.getId(), profile);
        return profile;
    }

    public Optional<Profile> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Profile> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void update(Profile profile) {
        Long id = profile.getId();
        if (id == null) return;
        storage.put(id, profile);
    }

    public boolean delete(Long id) {
        return storage.remove(id) != null;
    }
}
