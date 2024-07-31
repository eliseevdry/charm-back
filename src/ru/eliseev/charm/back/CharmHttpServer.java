package ru.eliseev.charm.back;

import ru.eliseev.charm.back.controller.LikeController;
import ru.eliseev.charm.back.controller.ProfileController;
import ru.eliseev.charm.back.model.Profile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharmHttpServer {
    private final int port;
    private final ExecutorService threadPool;

    private final ProfileController profileController;

    private final LikeController likeController;

    public CharmHttpServer(int port, int poolSize, ProfileController profileController, LikeController likeController) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(poolSize);
        this.profileController = profileController;
        this.likeController = likeController;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("--------------------Client connect--------------------");
                threadPool.submit(() -> processConnection(socket));
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processConnection(Socket socket) {
        try (socket;
             BufferedReader rqReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             DataOutputStream rsWriter = new DataOutputStream(socket.getOutputStream())) {

            while (!rqReader.ready()) ;
            String[] firstParams = null;
            while (rqReader.ready()) {
                String nextLine = rqReader.readLine();
                if (firstParams == null) {
                    firstParams = nextLine.split(" ");
                }
                System.out.println(nextLine);
            }

            String statusString = "404 Not Found";
            byte[] body = new byte[0];

            if (firstParams != null && firstParams.length == 3) {
                Map<String, String> queryParams = getQueryParams(firstParams[1]);
                String bodyString = null;
                if (firstParams[1].startsWith("/profile")) {
                    if ("GET".equals(firstParams[0])) {
                        if (queryParams.get("id") != null) {
                            Optional<Profile> maybeProfile = profileController.findById(Long.parseLong(queryParams.get("id")));
                            if (maybeProfile.isPresent()) bodyString = maybeProfile.get().toString();
                        } else {
                            bodyString = profileController.findAll().toString();
                        }
                    }
                } else if (firstParams[1].startsWith("/like")) {
                    if ("GET".equals(firstParams[0])) {
                        bodyString = likeController.count() + "";
                    }
                }
                if (bodyString != null) {
                    statusString = "200 OK";
                    body = "<p>%s</p>".formatted(bodyString).getBytes();
                }
            }

            byte[] startLine = "HTTP/1.1 %s\n".formatted(statusString).getBytes();
            byte[] headers = "Content-Type: text/html; charset=utf-8\nContent-Length: %s\n".formatted(body.length).getBytes();
            byte[] emptyLine = "\r\n".getBytes();

            rsWriter.write(startLine);
            rsWriter.write(headers);
            rsWriter.write(emptyLine);
            rsWriter.write(body);
            System.out.println("--------------------Client disconnect--------------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getQueryParams(String url) {
        // ?id=1&active=true
        Map<String, String> result = new HashMap<>();
        if (!url.contains("?")) return result;
        String[] queryParams = url.split("\\?")[1].split("&");
        for (String param : queryParams) {
            String[] pair = param.split("=");
            result.put(pair[0], pair[1]);
        }
        return result;
    }
}
