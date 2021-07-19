package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public Server() {
        final int PORT = 6587;
        Thread t1 = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server started on port " + PORT + " ...");
                System.out.println("Waiting for clients ...");
                while (true) {
                    Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    Scanner in = new Scanner(socket.getInputStream());
                    while (in.hasNextLine()) {
                        String input = in.nextLine();
                        if (input.equalsIgnoreCase("exit"))
                            break;
                        System.out.println("Received from client : " + input);
                        out.println("Im the server and i receive " + input);
                    }
                }
            } catch (IOException ignored) {
            }
        });
        t1.start();
    }
}
