package fr.arnoux23u.javano.network;

import fr.arnoux23u.javano.datatransfer.ActionHandler;
import fr.arnoux23u.javano.datatransfer.SerialOIS;
import fr.arnoux23u.javano.datatransfer.SerialOOS;
import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.mvc.model.Partie;
import fr.arnoux23u.javano.mvc.panels.ServerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Serializable {

    private static final long serialVersionUID = 68L;

    private Partie p;
    private ArrayList<ClientHandler> clients;

    public Server() {
        try {
            p = new Partie(this);
        } catch (Exception e) {
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
                        s = serverSocket.accept();
                        System.out.println("recu socket");
                        System.out.println("Created thread");
                        System.out.println("A new client is connected : " + s);
                        SerialOOS oos = new SerialOOS(s.getOutputStream());
                        SerialOIS ois = new SerialOIS(s.getInputStream());
                        System.out.println("Assigning new thread for this client");


                        ClientHandler ct = new ClientHandler(oos, ois, p);
                        clients.add(ct);

                        // Invoking the start() method
                        ct.start();
                            /*while (!ct.isInterrupted()) {
                            }
                            System.out.println("client : " + ct.joueur.getNom() + " disconnected ");
                            clients.remove(ct);*/


                    } catch (Exception e) {
                        /*try {
                            s.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }*/
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }

            }).start();
            System.out.println("Thread for accpet connections created");
        } catch (IOException exception) {
            System.out.println("pd");
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
        ActionHandler actionHandler = new ActionHandler("reachtest", null);
        while (true) {
            try {
                send(actionHandler);
                Thread.sleep(5000);
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
            System.out.println("send actionhandler");
            oos.writeObject(actionHandler);
        } catch (IOException exception) {
            switch (action) {
                case "reachtest" -> System.out.println("A client [" + this.joueur.getNom() + "] has lost connection");
                default -> System.err.println("Can't send actionhandler, disconnecting");
            }
            this.p.supprimerJoueur(this.joueur);
            this.stop();
        }
    }
}
