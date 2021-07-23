package network;

import datatransfer.ActionHandler;
import datatransfer.SerialOIS;
import datatransfer.SerialOOS;
import datatransfer.SerialSocket;
import game.Joueur;
import game.Partie;
import panels.ServerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server implements Serializable {

    private static final long serialVersionUID = 68L;

    private Partie p;
    private ArrayList<ClientHandler> clients;

    public Server() {
        try {
            p = new Partie(this);
        }catch (Exception e){
            System.err.println("Error occured when create a Partie");
            e.printStackTrace();
            System.exit(-1);
        }
        clients = new ArrayList<ClientHandler>();
        final int PORT = 6587;


        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur écoute sur le port : " + serverSocket.getLocalPort());

            ServerPanel sp = new ServerPanel(p, this);
            p.addObserver(sp);
            JFrame f = new JFrame();
            f.setTitle("JavaNO - Server");
            sp.setPreferredSize(new Dimension(1400, 800));
            f.setContentPane(sp);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.requestFocusInWindow();
            f.pack();
            f.setVisible(true);
            new Thread(() -> {
                while (true) {

                    Socket s = null;
                    try {
                        System.out.println("recu socket");
                        s = serverSocket.accept();
                        System.out.println("Created thread");
                        System.out.println("A new client is connected : " + s);
                        SerialOOS oos = new SerialOOS(s.getOutputStream());
                        SerialOIS ois = new SerialOIS(s.getInputStream());
                        System.out.println("Assigning new thread for this client");

                        // create a new thread object
                        ClientHandler ct = new ClientHandler(oos, ois, p);
                        clients.add(ct);

                        // Invoking the start() method
                        ct.start();
                        while (!ct.isInterrupted()) {
                        }
                        System.out.println("client : " + ct.joueur.getNom() + " disconnected ");
                        clients.remove(ct);

                    } catch (Exception e) {
                        /*try {
                            s.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }*/
                        e.printStackTrace();
                    }
                }

            }).start();
            System.out.println("Thread for accpet connections created");
        } catch (IOException exception) {
            System.out.println("pd");
        }

    }

    public void refreshClients() {
        for (ClientHandler c : clients) {
            try {
                System.out.println("Send repaint to client");
                c.oos.writeUTF("repaint");
                c.oos.writeObject(p);
                System.out.println("Envoie d'une partie ou p possede " + c.joueur.getCartes().size() + " cartes");
                System.out.println("Envoie d'une partie ou p2 possede " + p.getJoueurByName(c.joueur.getNom()).getCartes().size() + " cartes");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    public void sendToAllClients(Partie p) {
        for (ClientHandler c : clients) {
            c.send(new ActionHandler("repaint", p));
        }
    }

}

class ClientHandler extends Thread implements Serializable {

    private static final long serialVersionUID = 91L;

    final SerialOOS oos;
    final SerialOIS ois;
    Partie p;
    Joueur joueur;

    private String name;

    // Constructor
    public ClientHandler(SerialOOS oos, SerialOIS ois, Partie p) {
        this.oos = oos;
        this.p = p;
        this.ois = ois;
    }

    @Override
    public void run() {
        try {
            this.joueur = (Joueur) ois.readObject();
            System.out.println("J'ajoute " + joueur.getNom() + " a la liste");
            p.ajouterJoueur(joueur.getNom());
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            p.supprimerJoueur(joueur);
            System.err.println("ERROR WITH INSTANCIATION OF A CLIENT");
            this.stop();
        }
        while (true) {
            try {
                ActionHandler actionHandler = new ActionHandler("reachtest", null);
                send(actionHandler);
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Client disconnected because of error");
                p.supprimerJoueur(this.joueur);
                this.stop();
            }
        }
    }

    public void send(ActionHandler actionHandler) {
        String action = null;
        try {
            action = actionHandler.getAction();
            oos.writeObject(actionHandler);
        } catch (IOException exception) {
            switch (action) {
                case "reachtest":
                    System.out.println("A client [" + this.joueur.getNom() + "] has lost connection");
                    this.p.supprimerJoueur(this.joueur);
                    this.stop();
                default:
                    exception.printStackTrace();
                    this.stop();
            }
        }
    }
}
