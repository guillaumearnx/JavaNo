package fr.arnoux23u.javano.panels;

import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.game.Partie;
import fr.arnoux23u.javano.network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ServerPanel extends JPanel implements Observer {

    private Partie p;
    private Server s;

    public ServerPanel(Partie p, Server s) throws IOException {
        this.p = p;
        this.s = s;
        this.setLayout(new BorderLayout());
        this.add(new JButton("Lancer"){{
            setPreferredSize(new Dimension(20,20));
            addActionListener(e -> {
              p.distribuer();
              this.setText("PARTIE EN COURS");
              this.setEnabled(false);
            });
        }}, BorderLayout.AFTER_LAST_LINE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());
        int i = 100;
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        for(Joueur j : p.getJoueurs()){
            g.drawString(j.getNom(), (getWidth() - g.getFontMetrics().stringWidth(j.getNom())) / 2, i + getFont().getSize());
            i+=50;
        }
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        String connected = "Nombre de joueurs connectés : " + p.getConnectes();
        g.drawString(connected, (getWidth() - g.getFontMetrics().stringWidth(connected)) / 2, 40 + getFont().getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        p = (Partie) o;
        System.out.println("*Server update*");
        p.afficherJoueurs();
        repaint();
    }
}
