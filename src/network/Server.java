package network;

import data.*;
import game.Player;
import mvc.Game;
import mvc.views.*;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author arnoux23u
 */
public class Server {

    public static final String RED = "\033[0;31m";     // RED

    public static int tour = 0;

    private final ArrayList<ClientHandler> clients;

    public final int PORT = 6587;

    private Game game;

    public Server() {
        clients = new ArrayList<ClientHandler>();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            //Modele
            /*game = new Game();

            //Vues
            new Thread(()->{
                LaunchPanel launchPanel = new LaunchPanel(game);
                game.addObserver(launchPanel);
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setTitle("JavaNO - Server");
                f.setSize(800, 600);
                f.setContentPane(launchPanel);
                f.setVisible(true);
            });*/



            //Thread
            System.out.println(RED + "Serveur démarré et écoute sur le port : " + serverSocket.getLocalPort());
            while (true) {
                try {
                    Socket s = serverSocket.accept();
                    System.out.println(RED + "Created thread");
                    System.out.println(RED + "A new client is connected : " + s);
                    SerialOOS oos = new SerialOOS(s.getOutputStream());
                    SerialOIS ois = new SerialOIS(s.getInputStream());
                    ClientHandler ct = new ClientHandler(oos, ois, this);
                    clients.add(ct);
                    new Thread(ct).start();
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

    public synchronized void sendToAll(Object o) {
        for (ClientHandler c : clients) {
            c.sendData(o);
        }
    }

    public void removeClient(ClientHandler c) {
        clients.remove(c);
        game.removePlayer(c.uuid);
    }

    public Game getGame() {
        return game;
    }

}

class ClientHandler implements Runnable {

    String[] colors = new String[]{"\033[0;32m", "\033[0;33m", "\033[0;34m", "\033[0;35m", "\033[0;36m", "\033[0;37m"};

    final SerialOOS oos;
    final SerialOIS ois;

    private final Server server;
    private ClientInfo data;
    private final String color;
    private String name;
    public final UUID uuid;
    private final Scanner sc = new Scanner(System.in);

    // Constructor
    public ClientHandler(SerialOOS oos, SerialOIS ois, Server s) {
        uuid = UUID.randomUUID();
        this.oos = oos;
        this.server = s;
        this.ois = ois;
        this.color = colors[(int) (Math.random() * colors.length)];
    }

    @Override
    public void run() {
        try {
            name = String.valueOf(ois.readObject());
            System.out.println(color + "[CLIENT " + name + "] - Thread started");
            server.sendToAll("Welcome " + name + " !");
            server.getGame().addPlayer(uuid, name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            kill();
        }
        new Thread(() -> {
            try {
                while (true) {
                    System.out.println(color + "[CLIENT " + name + "] - Waiting for data");
                    System.out.println(color + "[CLIENT " + name + "] - Received data : " + ois.readObject());
                    Thread.sleep(100);
                    server.sendToAll("update");
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
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

    private void kill(){
        server.removeClient(this);
        System.out.println(color + "[CLIENT " + name + "] - Thread stopped");
    }


}
