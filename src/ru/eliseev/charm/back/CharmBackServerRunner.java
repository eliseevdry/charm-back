package ru.eliseev.charm.back;

import ru.eliseev.charm.back.controller.ProfileController;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.service.ProfileService;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CharmBackServerRunner {
    public static void main(String[] args) {
        ProfileController controller = new ProfileController(new ProfileService(new ProfileDao()));
        CharmHttpServer charmHttpServer = new CharmHttpServer(8080, 5, controller);
        charmHttpServer.run();
    }
}