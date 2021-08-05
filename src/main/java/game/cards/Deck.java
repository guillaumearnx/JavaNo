package main.java.game.cards;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Deck extends JPanel {

    private final ArrayList<Carte> cards;

    public Deck(){
        this.cards = new ArrayList<Carte>();
    }

    public void piocher(Carte c){
        cards.add(c);
    }

    public ArrayList<Carte> getCartes(){
        return cards;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*//p = client.getPartie();
        //Joueur j = client.getJoueur();
        this.removeAll();
        /////////////////////////////////////////////////////////////////////////////////////
        //
        //
        //
        //
        // ----------------------------------------------------------------------------------
        //
        //
        //
        /////////////////////////////////////////////////////////////////////////////////////
        deck = j.getCartes();
        Graphics2D g2d = (Graphics2D) g;
        final int w = getWidth();
        final int h = getHeight();
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        g2d.drawString("Name : " + j.getNom(), 100, 100);
        if (p != null) {
            int cardsnb = deck.size();
            System.out.println("Repaint : Player a " + cardsnb + " cartes dans son paquet");
            ArrayList<Carte> posees = p.getPosees();
            pioche = getLastPioche(p);
            if (j.equals(p.getActualPlayer())) {
                g2d.setColor(Color.green);
                g2d.fillRect(0, 0, 10, 10);
            }
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
                Point start = new Point(debutdeck + taillespaceindiv / 3 + (i * (taillecarte + taillespaceindiv)), getHeight() - getHeight() / 5);
                Point end = new Point(taillecarte, taillecarte + (taillecarte / 2));
                g2d.drawImage(c.image.toImage(), start.x , start.y, end.x, end.y, null);
            }
            for (int i = 0; i < posees.size(); i++) {
                Carte c = posees.get(i);
                g2d.drawImage(c.image.toImage(), w / 2 - (taillecarte / 2) + (i * 20), h / 2, taillecarte, taillecarte + (taillecarte / 2), null);
            }
        }
        //ACTU*/


    }
}
