package fr.arnoux23u.javano;

import fr.arnoux23u.javano.network.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class LaunchController {

    private int selectedMode = 1;

    @FXML
    private TextField name;

    @FXML
    public ToggleGroup mode;

    @FXML
    private RadioButton serverBtn, clientBtn;

    @FXML
    public void launchGame() {
        if (selectedMode == 1) {
            new Server();
        } else if (selectedMode == 0) {
            new Client(name.getText());
        }
    }

    @FXML
    public void setMode() {
        if (serverBtn.isSelected()) {
            selectedMode = 1;
            name.setVisible(false);
        } else if (clientBtn.isSelected()) {
            selectedMode = 0;
            name.setVisible(true);
            name.setText("Player" + String.format("%04d", new Random().nextInt(10000)));
        }
    }
}