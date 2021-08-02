package fr.arnoux23u.javano.network;

import fr.arnoux23u.javano.datatransfer.ActionHandler;
import fr.arnoux23u.javano.datatransfer.SerialSocket;
import fr.arnoux23u.javano.game.*;
import fr.arnoux23u.javano.game.Carte;
import fr.arnoux23u.javano.mvc.model.Partie;
import fr.arnoux23u.javano.mvc.panels.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;

public class Client {


    final String IP = "192.168.0.4";
    final int PORT = 6587;
    private Joueur j;

    private Partie p;

    public Client(String name) {

        try {
            // getting localhost ip
            InetAddress ip = InetAddress.getByName(IP);

            // establish the connection with server port 5056
            SerialSocket s = new SerialSocket(ip, PORT);

            // obtaining input and out streams
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            try {
                j = new Joueur(name);
                System.out.println("Envoi de jouerur : " + name);
                oos.writeObject(j);
                /*System.out.println("Reception partie");
                p = (Partie) ois.readObject();
                System.out.println("Recu partie");*/


            } catch (IOException e) {
                e.printStackTrace();
            }
            //FRAME
            ClientPanel cp = new ClientPanel(this);
            p.addObserver(cp);
            JFrame f = new JFrame();
            f.setTitle("JavaNo - Client");
            cp.setPreferredSize(new Dimension(1400, 800));
            f.setContentPane(cp);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.requestFocusInWindow();
            f.pack();
            f.setVisible(true);

            // the following loop performs the exchange of
            // information between client and client handler
            /*new Thread(() -> {
                System.out.println("Client : " + name + " started..");
                while (true) {
                    try {
                        System.out.println("Read action handler");
                        ActionHandler actionHandler = (ActionHandler) ois.readObject();
                        String action = actionHandler.getAction();
                        System.out.println(actionHandler.getObject());
                        System.out.println("read  -> " + action);
                        switch (action) {
                            case "repaint":
                                p = (Partie) actionHandler.getObject();
                                System.out.println("need to update player data");
                                this.j = p.getJoueurByName(name);
                                p.afficherJoueurs();
                                cp.repaint();
                                break;
                            default:
                                break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Erreur");
                        System.exit(-1);
                    }
                }
            }).start();*/
            // closing resources
            /*dis.close();
            dos.close();*/
        } catch (
                Exception e) {
            e.printStackTrace();
        }


    }


    public Partie getPartie() {
        return p;
    }

    public Joueur getJoueur() {
        return j;
    }

    public boolean poserCarte(Carte c) {

        if (p.getPioche().remove(c))
            p.getPosees().add(c);
        return true;
    }

}

