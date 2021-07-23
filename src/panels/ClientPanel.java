package panels;

import game.Joueur;
import game.Partie;
import game.utils.Carte;
import network.Client;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
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
        Graphics2D g2d = (Graphics2D) g;
        final int w = getWidth();
        final int h = getHeight();
        g2d.drawRect(0, 0, w, h);
        Joueur j = client.getJoueur();
        //System.out.println("Repaint");
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        g2d.drawString("Name : " + j.getNom(), 100, 100);
        ArrayList<Carte> pc = j.getCartes();
        int cardsnb = pc.size();
        System.out.println("Repaint : Player a "+cardsnb+" cartes dans son paquet");
        if(cardsnb>0) {
            int tailledeck = (cardsnb * (w / 9)) + (10 * (cardsnb - 1));
            int debutdeck = (w / 2 - tailledeck / 2);
            int tailleespacestotaux = ((cardsnb - 1) * (w / 40));
            int taillespaceindiv = tailleespacestotaux / cardsnb - 1;
            final int taillecarte = (tailledeck - tailleespacestotaux) / cardsnb;
            //System.out.println("Joueur ["+j.getNom()+"] possede "+cardsnb+" cartes");
            int decalage = tailledeck - tailleespacestotaux;
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < cardsnb; i++) {
                Carte c = pc.get(i);
                g2d.setColor(c.color);
            /*g2d.fillRect(taillespaceindiv+ debutdeck + (i * taillecarte) + (((tailleespacestotaux) / cardsnb) * i), h*3/4, taillecarte, h/5);
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawString(Carte.getStringFromValue(c.value), 5+taillespaceindiv+ debutdeck + (i * taillecarte) + (((tailleespacestotaux) / cardsnb) * i), h*3/4+(taillecarte*(3/4))+taillecarte/3*2);
            g2d.drawOval(5+taillespaceindiv+ debutdeck + (i * taillecarte) + (((tailleespacestotaux) / cardsnb) * i), h*3/4+(taillecarte*(3/4))+taillecarte/3*2, taillecarte-10, h/5-(taillecarte));*/
                g2d.drawImage(c.image, debutdeck + i * taillecarte, getHeight() - getHeight() / 5, taillecarte, taillecarte, null);
            }
        }
    }
}
