package ru.eliseev.charm.back;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CharmBackClientRunner {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
             DataOutputStream requestStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream responseStream = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)
        ) {
            while (scanner.hasNextLine()) {
                String request = scanner.nextLine();
                requestStream.writeUTF(request);
                String response = responseStream.readUTF();
                System.out.println(response);
            }
        }
    }
}
