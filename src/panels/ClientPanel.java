package panels;

import game.Joueur;
import game.Partie;
import game.utils.Carte;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientPanel extends JPanel {


    private Client client;
    private Partie partie;
    private Joueur j;
    private String name;

    public ClientPanel(Client client, String name){
        this.name = name;
        this.client = client;
        this.partie = client.getPartie();
        this.j = partie.getJoueurByName(name);
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.partie = client.getPartie();
        g.drawString("Name : "+name,100,100);
        ArrayList<Carte> pc = j.getCartes();
        for(int i = 0; i < pc.size(); i++){
            Carte c = pc.get(i);
            g.setColor(c.color);
            g.drawRect(10+(i*40), 200,15,40);
        }
    }
}
