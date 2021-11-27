package fr.arnoux23u.javano;

import fr.arnoux23u.javano.network.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * Controller for the main window
 *
 * @author arnoux23u
 */
public class LaunchController {

    @FXML
    private TextField name;

    @FXML
    private RadioButton serverBtn;

    @FXML
    private void launchGame() throws IOException {
        FXMLLoader fxmlLoader;
        if (serverBtn.isSelected()) {
            fxmlLoader = new FXMLLoader(LaunchPanel.class.getResource("server.fxml"));
            new Server();
        } else {
            fxmlLoader = new FXMLLoader(LaunchPanel.class.getResource("client.fxml"));
            new Client(name.getText());
        }
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage stage = (Stage) name.getScene().getWindow();
        stage.setTitle("JavaNo - " + (serverBtn.isSelected() ? "Server" : name.getText()));
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    private void setMode() {
        name.setVisible(!serverBtn.isSelected());
        name.setText("Player" + String.format("%04d", new Random().nextInt(10000)));
    }
}
