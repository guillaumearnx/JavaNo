package network;

import game.Partie;
import panels.ServerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {

    private Partie p;

    public Server() {
        p = new Partie();

        final int PORT = 6587;


        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur écoute sur le port : " + serverSocket.getLocalPort());

            ServerPanel sp = new ServerPanel(p);
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
                    System.out.println("Created thread");
                    Socket s = null;
                    try {
                        s = serverSocket.accept();
                        System.out.println("A new client is connected : " + s);
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        p.ajouterJoueur(String.valueOf(s.getInetAddress()));


                        System.out.println("Assigning new thread for this client");

                        // create a new thread object
                        Thread t = new ClientHandler(s, dis, dos, p);

                        // Invoking the start() method
                        t.start();

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
            System.out.println("fini");
        } catch (IOException exception) {
            System.out.println("pd");
        }

    }
}

class ClientHandler extends Thread {

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final Partie p;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Partie p) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.p = p;
    }

    @Override
    public void run() {
        while (true)
            try {
                dos.writeUTF("Test");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("a pu");
                p.supprimerJoueur(String.valueOf(s.getInetAddress()));
                this.stop();
            }
    }

}
