package fr.arnoux23u.javano.mvc.views;

import fr.arnoux23u.javano.mvc.Game;
import fr.arnoux23u.javano.mvc.Model;
import fr.arnoux23u.javano.mvc.Observer;
import javafx.fxml.FXML;

import java.awt.*;

/**
 * @author arnoux23u
 */
public class ServerView implements Observer {

    private Game game;

    @FXML private TextArea clientsList;

    public ServerView(Game game) {
        this.game = game;
    }

    @Override
    public void update(Model m) {
        game = (Game) m;
        clientsList.setText(game.getPlayers().toString());
    }

}
