package ru.eliseev.charm.back.controller;

import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.service.ProfileService;

public class ProfileController {
    private final ProfileService service;
    public ProfileController(ProfileService service) {
        this.service = service;
    }
}
