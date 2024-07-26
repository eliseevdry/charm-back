package ru.eliseev.charm.back;

import ru.eliseev.charm.back.controller.ProfileController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharmHttpServer {
    private final int port;
    private final ExecutorService threadPool;

    private final ProfileController profileController;

    public CharmHttpServer(int port, int poolSize, ProfileController profileController) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(poolSize);
        this.profileController = profileController;
    }

    public void run() {
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
            
            while (!rqReader.ready());
            while (rqReader.ready()) {
                System.out.println(rqReader.readLine());
            }
            
            byte[] body = "<p>Hello from Charm!</p>".getBytes();
            byte[] startLine = "HTTP/1.1 200 OK\n".getBytes();
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
}
