module fr.arnoux23u.javano {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens fr.arnoux23u.javano to javafx.fxml;
    opens fr.arnoux23u.javano.mvc.views to javafx.fxml;
    exports fr.arnoux23u.javano;
    exports fr.arnoux23u.javano.data;
    exports fr.arnoux23u.javano.mvc;
    exports fr.arnoux23u.javano.mvc.controllers;
    exports fr.arnoux23u.javano.mvc.views;
    exports fr.arnoux23u.javano.game;
    exports fr.arnoux23u.javano.network;
}