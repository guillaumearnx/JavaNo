package fr.arnoux23u.javano.panels;

import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.game.Partie;
import fr.arnoux23u.javano.game.utils.Carte;
import fr.arnoux23u.javano.game.utils.CartePanel;
import fr.arnoux23u.javano.network.Client;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientPanel extends JPanel {


    private Client client;

    private ArrayList<Carte> pioche;
    private ArrayList<Carte> deck;
    private int debutdeck;
    private int taillecarte;

    public ClientPanel(Client client, String name) {
        this.client = client;
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Partie p = client.getPartie();
        Joueur j = client.getJoueur();
        this.removeAll();
        deck = j.getCartes();
        pioche = getLastPioche(p);
        for (int i = 0; i < pioche.size(); i++) {
            this.add(new CartePanel(pioche.get(i), this, i));
        }
        for (int i = 0; i < deck.size(); i++) {
            this.add(new CartePanel(deck.get(i), this, i));
        }

        Graphics2D g2d = (Graphics2D) g;
        final int w = getWidth();
        final int h = getHeight();
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        g2d.drawString("Name : " + j.getNom(), 100, 100);

        //ACTU
        if (j.equals(p.getActualPlayer()))
            g2d.setColor(Color.green);
        g2d.fillRect(0, 0, 10, 10);

        int cardsnb = deck.size();
        System.out.println("Repaint : Player a " + cardsnb + " cartes dans son paquet");
        if (cardsnb > 0) {
            ArrayList<Carte> posees = p.getPosees();
            int tailledeck = (cardsnb * (w / 9)) + (10 * (cardsnb - 1));
            int tailleespacestotaux = ((cardsnb - 1) * (w / 40));
            final int taillecarte = (tailledeck - tailleespacestotaux) / cardsnb;
            int debutdeck = (w / 2 - tailledeck / 2);
            int taillespaceindiv = tailleespacestotaux / cardsnb - 1;
            g2d.drawRect(debutdeck, h - 200, tailledeck, h - 100);
            int decalage = tailledeck - tailleespacestotaux;
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < cardsnb; i++) {
                //Carte c = deck.get(i);
                //g2d.drawImage(c.image.toImage(), debutdeck + i * taillecarte, getHeight() - getHeight() / 5, taillecarte, taillecarte + (taillecarte / 2), null);
            }
            g2d.drawImage(posees.get(0).image.toImage(), w / 2 - (taillecarte / 2), h / 2, taillecarte, taillecarte + (taillecarte / 2), null);
        }
    }

    private ArrayList<Carte> getLastPioche(Partie p) {
        ArrayList<Carte> temp = p.getPosees();
        return new ArrayList<Carte>() {{
            add(temp.get(temp.size() - 1));
            add(temp.get(temp.size() - 2));
            add(temp.get(temp.size() - 3));
        }};
    }

    public int getDebutdeck() {
        return debutdeck;
    }

    public int getTaillecarte() {
        return taillecarte;
    }
}
