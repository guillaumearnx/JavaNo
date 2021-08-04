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

    @Serial
    private static final long serialVersionUID = 68L;

    private Partie p;

    private final ArrayList<ClientHandler> clients;

    public final int PORT = 6587;

    public Server() {
        clients = new ArrayList<ClientHandler>();
        try {
            p = new Partie();
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré et écoute sur le port : " + serverSocket.getLocalPort());
            ServerPanel sp = new ServerPanel(this);
            creerPanel(sp);
            p.addObserver(sp);
            createThread(serverSocket);
            System.out.println("Thread for accpet connections created");
        } catch (IOException exception) {
            System.err.println("Erreur");
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

    private void creerPanel(ServerPanel sp) {
        JFrame f = new JFrame();
        f.setTitle("JavaNO - Server");
        sp.setPreferredSize(new Dimension(1400, 800));
        f.setContentPane(sp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.requestFocusInWindow();
        f.pack();
        f.setVisible(true);
    }

    public void sendToAllClients(ActionHandler ah) {
        for (ClientHandler c : clients)
            c.send(ah);
    }

    public Partie getPartie() {
        return p;
    }

    public void updatePartie(Partie o) {
        this.p = o;
        sendToAllClients(new ActionHandler("update", p));
    }
}

@SuppressWarnings("deprecation")
class ClientHandler extends Thread implements Serializable {

    @Serial
    private static final long serialVersionUID = 91L;

    final SerialOOS oos;
    final SerialOIS ois;

    private Joueur joueur;
    private final Server server;
    // Constructor
    public ClientHandler(SerialOOS oos, SerialOIS ois, Server s) {
        this.oos = oos;
        this.server = s;
        this.ois = ois;
    }

    @Override
    public void run() {
        Partie p = server.getPartie();
        try {
            this.joueur = (Joueur) ois.readObject();
            System.out.println("J'ajoute " + joueur.getNom() + " a la liste");
            p.ajouterJoueur(joueur.getNom());
            oos.writeObject(p);
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            p.supprimerJoueur(joueur);
            System.err.println("ERROR WITH INSTANCIATION OF A CLIENT");
            this.stop();
        }
        /*ActionHandler action;
        while (true) {
            try {
                action = receive();
                switch (action.getAction()) {
                    case "update" -> {
                        //ACTUALISER QQCH
                    }
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("Client disconnected because of error");
                exit();
            }
        }*/
    }

    public ActionHandler receive() {
        ActionHandler result = null;
        try {
            result = (ActionHandler) ois.readObject();
            System.out.println("[" + joueur.getNom() + "] receive update from client");
        } catch (IOException | ClassNotFoundException exception) {
            System.err.println("cant read update");
            exit();
        }
        return result;
    }

    public void send(ActionHandler actionHandler) {
        try {
            oos.writeObject(actionHandler);
        } catch (IOException exception) {
            System.out.println("Can't send actionhandler, disconnecting");
            System.err.println("A client [" + this.joueur.getNom() + "] has lost connection");
            exit();
        }
    }

    private void exit() {
        Partie p = server.getPartie();
        p.supprimerJoueur(this.joueur);
        this.stop();
        if (p.getConnectes() < 1) {
            p.arreter();
        }
    }
}
