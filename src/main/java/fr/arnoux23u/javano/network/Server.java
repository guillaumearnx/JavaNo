package fr.arnoux23u.javano.network;

import fr.arnoux23u.javano.data.*;
import fr.arnoux23u.javano.mvc.*;
import fr.arnoux23u.javano.mvc.views.ServerView;
import javafx.application.Application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author arnoux23u
 */
public class Server {

    public static final String RED = "\033[0;31m";

    //public static int tour = 0;

    private final ArrayList<ClientHandler> clients;

    public final int PORT = 6587;

    private Game game;

    public Server() {
        clients = new ArrayList<>();
        try {
            game = new Game();
            ServerView sv = new ServerView(game);
            game.addObserver(sv);


            ServerSocket serverSocket = new ServerSocket(PORT);
            //Thread
            System.out.println(RED + "Serveur démarré et écoute sur le port : " + serverSocket.getLocalPort());
            new Thread(() -> {
                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        System.out.println(RED + "Created thread");
                        System.out.println(RED + "A new client is connected : " + s);
                        SerialOOS oos = new SerialOOS(s.getOutputStream());
                        SerialOIS ois = new SerialOIS(s.getInputStream());
                        ClientHandler ct = new ClientHandler(oos, ois, this);
                        clients.add(ct);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }).start();
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    /*public synchronized void sendToAll(Object o) {
        for (ClientHandler c : clients) {
            c.sendData(o);
        }
    }

    public synchronized void removeClient(ClientHandler c) {
        clients.remove(c);
        game.removePlayer(c.uuid);
    }*/

    public synchronized Game getGame() {
        return game;
    }

}

class ClientHandler {

    String[] colors = new String[]{"\033[0;32m", "\033[0;33m", "\033[0;34m", "\033[0;35m", "\033[0;36m", "\033[0;37m"};

    final SerialOOS oos;
    final SerialOIS ois;

    private final Server server;
    private final String color;
    private String name;
    public final UUID uuid;
    private final Scanner scanner;

    // Constructor
    public ClientHandler(SerialOOS oos, SerialOIS ois, Server s) {
        uuid = UUID.randomUUID();
        this.oos = oos;
        this.server = s;
        scanner = new Scanner(System.in);
        this.ois = ois;
        this.color = colors[(int) (Math.random() * colors.length)];
        run();
    }

    private void run() {
        try {
            name = String.valueOf(ois.readObject());
            System.out.println(color + "[CLIENT " + name + "] - Thread started");
            //server.sendToAll("Welcome " + name + " !");
            server.getGame().addPlayer(uuid, name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            kill();
        }
        new Thread(() -> {
            try {
                while (true) {
                    System.out.println(color + "[CLIENT " + name + "] - Received data : " + ois.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                kill();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    String str = scanner.nextLine();
                    System.out.println(color + "[CLIENT " + name + "] - Sended data : " + str);
                    oos.writeObject(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
                kill();
            }
        }).start();
    }

    public void sendData(Object o) {
        try {
            System.out.println(color + "[CLIENT " + name + "] - Sending UPDATE");
            oos.writeObject(o);
        } catch (IOException e) {
            kill();
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    private void kill() {
        //server.removeClient(this);
        System.out.println(color + "[CLIENT " + name + "] - Thread stopped");

    }


}
