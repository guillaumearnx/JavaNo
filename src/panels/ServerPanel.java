package panels;

import game.Partie;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ServerPanel extends JPanel implements Observer {


    private Partie p;

    public ServerPanel() throws IOException {
        this.setLayout(new BorderLayout());
        System.out.println("haha");
        p = new Partie();
        p.addObserver(this);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        String connected = "Nombre de joueurs connectés : " + p.getConnectes();
        g.drawString(connected, (getWidth() - g.getFontMetrics().stringWidth(connected)) / 2, 40 + getFont().getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        p = (Partie) o;
        repaint();
    }
}
