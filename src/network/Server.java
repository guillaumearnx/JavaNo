package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        final int PORT = 6587;
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT + " ...");
        System.out.println("Wainting for clients ...");
        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread() {
                @Override
                public void run() {
                    try (
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            Scanner in = new Scanner(socket.getInputStream());
                    ) {
                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            if (input.equalsIgnoreCase("exit"))
                                break;
                            System.out.println("Received from client : " + input);
                            out.println("Im the server and i receive " + input);
                        }
                    } catch (IOException ignored) {
                    }
                }
            };
            t.start();
        }
    }
}
