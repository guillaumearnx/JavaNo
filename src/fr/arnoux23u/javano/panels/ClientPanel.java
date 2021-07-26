package fr.arnoux23u.javano.panels;

import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.game.Partie;
import fr.arnoux23u.javano.game.utils.Carte;
import fr.arnoux23u.javano.network.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientPanel extends JPanel {


    private Client client;

    public ClientPanel(Client client, String name) {
        this.client = client;
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Partie p = client.getPartie();
        Joueur j = client.getJoueur();
        Graphics2D g2d = (Graphics2D) g;
        final int w = getWidth();
        final int h = getHeight();
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        g2d.drawString("Name : " + j.getNom(), 100, 100);
        ArrayList<Carte> deck = j.getCartes();
        ArrayList<Carte> posees = p.getPosees();
        int cardsnb = deck.size();
        System.out.println("Repaint : Player a " + cardsnb + " cartes dans son paquet");
        if (cardsnb > 0) {
            int tailledeck = (cardsnb * (w / 9)) + (10 * (cardsnb - 1));
            int tailleespacestotaux = ((cardsnb - 1) * (w / 40));
            final int taillecarte = (tailledeck - tailleespacestotaux) / cardsnb;
            int debutdeck = (w / 2 - tailledeck / 2);
            int taillespaceindiv = tailleespacestotaux / cardsnb - 1;
            g2d.drawRect(debutdeck, h - 200, tailledeck, h - 100);
            int decalage = tailledeck - tailleespacestotaux;
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < cardsnb; i++) {
                Carte c = deck.get(i);
                g2d.drawImage(c.image.toImage(), debutdeck + i * taillecarte, getHeight() - getHeight() / 5, taillecarte, taillecarte + (taillecarte / 2), null);
            }
            g2d.drawImage(posees.get(0).image.toImage(), w/2-(taillecarte/2),h/2,taillecarte,taillecarte+(taillecarte/2), null);
        }
    }
}
