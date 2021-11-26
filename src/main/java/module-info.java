module fr.arnoux23u.javano {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens fr.arnoux23u.javano to javafx.fxml;
    exports fr.arnoux23u.javano;
}