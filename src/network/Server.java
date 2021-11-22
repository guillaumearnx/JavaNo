package network;

import data.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author arnoux23u
 */
public class Server {

    private final ArrayList<ClientHandler> clients;

    Scanner sc = new Scanner(System.in);

    public final int PORT = 6587;

    public Server() {
        clients = new ArrayList<ClientHandler>();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré et écoute sur le port : " + serverSocket.getLocalPort());

            while (true) {
                try {
                    Socket s = serverSocket.accept();
                    System.out.println("Created thread");
                    System.out.println("A new client is connected : " + s);
                    SerialOOS oos = new SerialOOS(s.getOutputStream());
                    SerialOIS ois = new SerialOIS(s.getInputStream());
                    ClientHandler ct = new ClientHandler(s, oos, ois, this);
                    clients.add(ct);
                    ct.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }


        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

}

class ClientHandler extends Thread {

    final SerialOOS oos;
    final SerialOIS ois;

    private final Server server;
    private final Socket socket;

    // Constructor
    public ClientHandler(Socket socket, SerialOOS oos, SerialOIS ois, Server s) {
        this.socket = socket;
        this.oos = oos;
        this.server = s;
        this.ois = ois;
    }

    @Override
    public void run() {
        System.out.println("[CLIENT HANDLER] - Thread started");
        while (true) {
            try {
                System.out.println(ois.readObject().toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
