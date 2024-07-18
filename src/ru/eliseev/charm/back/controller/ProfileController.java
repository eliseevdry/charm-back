package ru.eliseev.charm.back.controller;

import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.service.ProfileService;

import java.util.Optional;

public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    public String save(String request) {
        String[] strings = request.split(",");
        if (strings.length != 4) return "Bad request: need 4 parameters to save profile.";

        Profile profile = new Profile();
        profile.setEmail(strings[0]);
        profile.setName(strings[1]);
        profile.setSurname(strings[2]);
        profile.setAbout(strings[3]);

        return service.save(profile).toString();
    }

    public String findById(String request) {
        String[] strings = request.split(",");
        if (strings.length != 1) return "Bad request: need one number parameter";

        long id;
        try {
            id = Long.parseLong(strings[0]);
        } catch (NumberFormatException e) {
            return "Bad request: can`t parse string [" + strings[0] + "] to long.";
        }

        Optional<Profile> maybeProfile = service.findById(id);

        if (maybeProfile.isEmpty()) return "Not found";

        return maybeProfile.get().toString();
    }
    
    public String findAll() {
        return service.findAll().toString();
    }
    
    public String update(String request) {
        String[] strings = request.split(",");
        if (strings.length != 5) return "Bad request: need 5 parameters to update profile.";

        long id;
        try {
            id = Long.parseLong(strings[0]);
        } catch (NumberFormatException e) {
            return "Bad request: can`t parse string [" + strings[0] + "] to long.";
        }

        Profile profile = new Profile();
        profile.setId(id);
        profile.setEmail(strings[1]);
        profile.setName(strings[2]);
        profile.setSurname(strings[3]);
        profile.setAbout(strings[4]);

        service.update(profile);

        return "Update success";
    }

    public String delete(String request) {
        String[] strings = request.split(",");
        if (strings.length != 1) return "Bad request: need one number parameter";

        long id;
        try {
            id = Long.parseLong(strings[0]);
        } catch (NumberFormatException e) {
            return "Bad request: can`t parse string [" + strings[0] + "] to long.";
        }

        boolean result = service.delete(id);

        if (!result) return "Not found";

        return "Delete success";
    }
}
