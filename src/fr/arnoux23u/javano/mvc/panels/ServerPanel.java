package fr.arnoux23u.javano.mvc.panels;

import fr.arnoux23u.javano.datatransfer.ActionHandler;
import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.mvc.model.Partie;
import fr.arnoux23u.javano.network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ServerPanel extends JPanel implements Observer {

    private Server s;

    private final JButton jb = new JButton("Lancer") {{
        setPreferredSize(new Dimension(20, 20));
        addActionListener(e -> {
            s.getPartie().lancer();
        });
    }};

    public ServerPanel(Server s) throws IOException {
        this.s = s;
        this.setLayout(new BorderLayout());
        this.add(jb, BorderLayout.AFTER_LAST_LINE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Partie p = s.getPartie();
        jb.setText(p.isEnCours() ? "PARTIE EN COURS" : "Lancer");
        jb.setEnabled(!p.isEnCours() && p.getConnectes() > 0);
        g.clearRect(0, 0, getWidth(), getHeight());
        int i = 100;
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        for (Joueur j : p.getJoueurs()) {
            g.drawString(j.getNom(), (getWidth() - g.getFontMetrics().stringWidth(j.getNom())) / 2, i + getFont().getSize());
            i += 50;
        }
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        String connected = "Nombre de joueurs connectés : " + p.getConnectes();
        g.drawString(connected, (getWidth() - g.getFontMetrics().stringWidth(connected)) / 2, 40 + getFont().getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        s.updatePartie((Partie)o);
    }
}
