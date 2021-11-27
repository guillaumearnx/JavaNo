package fr.arnoux23u.javano;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the application
 *
 * @author arnoux23u
 */
public class LaunchPanel extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LaunchPanel.class.getResource("launchpanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("JavaNo - Launcher");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Run method
     *
     * @param args args provided by the command line
     */
    public static void main(String[] args) {
        launch(args);
    }

}
