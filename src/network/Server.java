package network;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Server implements Serializable {

    private static final long serialVersionUID = 68L;

    private Partie p;
    private ArrayList<ClientHandler> clients;

    public Server() {
        p = new Partie(this);
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
                        System.out.println("Created thread");
                        System.out.println("A new client is connected : " + s);
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        System.out.println("Assigning new thread for this client");

                        // create a new thread object
                        ClientHandler ct = new ClientHandler(s, oos, ois, p);
                        clients.add(ct);

                        // Invoking the start() method
                        ct.start();

                    } catch (Exception e) {
                        assert s != null;
                        try {
                            s.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
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
                System.out.println("Envoie d'une partie ou p possede "+c.joueur.getCartes().size()+" cartes");
                System.out.println("Envoie d'une partie ou p2 possede "+p.getJoueurByName(c.joueur.getNom()).getCartes().size()+" cartes");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    public void sendToAllClients(Partie p){
        for(ClientHandler c : clients){
                c.send(p);
        }
    }

}

class ClientHandler extends Thread {

    final ObjectOutputStream oos;
    final ObjectInputStream ois;
    final Socket s;
    Partie p;
    Joueur joueur;

    private String name;

    // Constructor
    public ClientHandler(Socket s, ObjectOutputStream oos, ObjectInputStream ois, Partie p) {
        this.s = s;
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
            //p.ajouterJoueur(name);

            /*System.out.println("Envoie partie");
            oos.writeObject(p);*/


        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            System.err.println("Cant read name of client");
            ;
        }
        //while (true)
            /*try {

                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Client disconnected");
                p.supprimerJoueur(name);
                this.stop();
            }*/
    }

    public void send(Partie p){
        try {
            oos.writeObject(p);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
