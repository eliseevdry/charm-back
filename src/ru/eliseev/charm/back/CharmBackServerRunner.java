package ru.eliseev.charm.back;

import ru.eliseev.charm.back.controller.LikeController;
import ru.eliseev.charm.back.controller.ProfileController;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.service.ProfileService;

public class CharmBackServerRunner {
    public static void main(String[] args) {
        ProfileController profileController = new ProfileController(new ProfileService(new ProfileDao()));
        
        
        
        LikeController likeController = new LikeController();
        CharmHttpServer charmHttpServer = new CharmHttpServer(8080, 5, profileController, likeController);
        charmHttpServer.start();
    }
}