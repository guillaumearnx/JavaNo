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

    public static int tour = 0;

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

    public void sendToAll(String msg) {
        for (ClientHandler c : clients) {
            c.send(msg);
        }
    }

}

class ClientHandler extends Thread {

    final SerialOOS oos;
    final SerialOIS ois;

    private final Server server;
    private final Socket socket;
    private String name;

    // Constructor
    public ClientHandler(Socket socket, SerialOOS oos, SerialOIS ois, Server s) {
        this.socket = socket;
        this.oos = oos;
        this.server = s;
        this.ois = ois;
    }

    @Override
    public void run() {
        //LANCEE QUAND UN CLIENT SE CONNECTE
        try {
            name = String.valueOf(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("[CLIENT " + name + "] - Thread started");
        server.sendToAll("[SERVER] - " + name + " is connected");

        while (true) {
            try {
                System.out.println("[CLIENT " + name + "] : " + ois.readObject().toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String msg) {
        try {
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
