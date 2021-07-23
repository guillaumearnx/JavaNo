package network;

import datatransfer.ActionHandler;
import datatransfer.SerialSocket;
import game.*;
import panels.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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
            ClientPanel cp = new ClientPanel(this, name);
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
            new Thread(() -> {
                System.out.println("Client : " + name + " started..");
                while (true) {
                    try {
                        ActionHandler actionHandler = (ActionHandler) ois.readObject();
                        String action = actionHandler.getAction();
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
                        System.out.println("Erreur");
                        e.printStackTrace();
                        //System.exit(-1);
                    }
                }
            }).start();
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
}

