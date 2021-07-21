package network;

import datatransfer.SerialSocket;
import game.Partie;
import panels.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {



    final String IP = "192.168.0.4";
    final int PORT = 6587;

    private Partie p;

    public Client(String name) {

        try {
            // getting localhost ip
            InetAddress ip = InetAddress.getByName(IP);

            // establish the connection with server port 5056
            SerialSocket s = new SerialSocket(ip, PORT);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            try {
                System.out.println("Reception partie");
                p = (Partie) ois.readObject();
                System.out.println("Envoi nom");
                dos.writeUTF(name);

            } catch (IOException | ClassNotFoundException e) {
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
                while (true){
                    try {
                        String action = dis.readUTF();
                        switch (action){
                            case "repaint":
                                p = (Partie) ois.readObject();
                                cp.repaint();
                                break;
                        }
                    }catch (IOException | ClassNotFoundException e){
                        System.out.println("Erreur");
                        System.exit(-1);
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
}

