package network;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author arnoux23u
 */
public class Server {

    private final ArrayList<ClientHandler> clients;

    public final int PORT = 6587;

    public Server() {
        clients = new ArrayList<ClientHandler>();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré et écoute sur le port : " + serverSocket.getLocalPort());
            createThread(serverSocket);
            System.out.println("Thread for accpet connections created");
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    private void createThread(ServerSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                Socket s;
                try {
                    s = serverSocket.accept();
                    System.out.println("recu socket");
                    System.out.println("Created thread");
                    System.out.println("A new client is connected : " + s);
                    SerialOOS oos = new SerialOOS(s.getOutputStream());
                    SerialOIS ois = new SerialOIS(s.getInputStream());
                    System.out.println("Assigning new thread for this client");
                    ClientHandler ct = new ClientHandler(oos, ois, this);
                    clients.add(ct);
                    ct.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }).start();
    }

}

class ClientHandler extends Thread implements Serializable {

    final SerialOOS oos;
    final SerialOIS ois;

    private final Server server;

    // Constructor
    public ClientHandler(SerialOOS oos, SerialOIS ois, Server s) {
        this.oos = oos;
        this.server = s;
        this.ois = ois;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
    }

}
