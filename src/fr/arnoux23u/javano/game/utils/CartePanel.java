package fr.arnoux23u.javano.game.utils;

import fr.arnoux23u.javano.panels.ClientPanel;

import javax.swing.*;
import java.awt.*;

public class CartePanel extends JPanel {

    private ClientPanel parent;
    private Carte carte;
    private int position;

    public CartePanel(Carte c, ClientPanel parent, int i){
        this.carte = c;
        this.parent = parent;
        this.position = i;
    }

    public Carte getCarte() {
        return carte;
    }

    @Override
    public JPanel getParent() {
        return parent;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int taillecarte = parent.getTaillecarte();
        int debutdeck = parent.getDebutdeck();
        g.drawImage(carte.image.toImage(), debutdeck + position * taillecarte, parent.getHeight() - parent.getHeight() / 5, taillecarte, taillecarte + (taillecarte / 2), null);
    }
}
