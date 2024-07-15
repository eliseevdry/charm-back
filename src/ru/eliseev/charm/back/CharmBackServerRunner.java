package ru.eliseev.charm.back;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CharmBackServerRunner {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket socket = serverSocket.accept();
             DataInputStream requestStream = new DataInputStream(socket.getInputStream());
             DataOutputStream responseStream = new DataOutputStream(socket.getOutputStream())
             ) {
            String request = requestStream.readUTF();
            
            while(!"stop".equals(request)) {
                System.out.println("Client request: " + request);
                responseStream.writeUTF("Hello from Charm! Your message: " + request);
                request = requestStream.readUTF();
            }
        }
    }
}