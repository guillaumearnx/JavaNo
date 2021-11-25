package mvc.views;

import game.Player;
import mvc.Game;
import mvc.Model;
import mvc.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * @author arnoux23u
 */
public class LaunchPanel extends JPanel implements Observer {

    private Game game;

    private final JTextArea players;

    public LaunchPanel(Game game) {
        setPreferredSize(new Dimension(500, 500));
        setLayout(new BorderLayout());
        this.game = game;
        players = new JTextArea() {{
            setEditable(false);
            setFont(new Font("Arial", Font.PLAIN, 20));
            setText("Players :\n");
        }};
        add(players, BorderLayout.CENTER);
        System.out.println(players.getText());
    }

    @Override
    public void update(Model model) {
        game = (Game) model;
        players.setText("Players :\n");
        for (Player p : game.getPlayers()) {
            players.append((p.name) + "\n");
        }
    }
}
