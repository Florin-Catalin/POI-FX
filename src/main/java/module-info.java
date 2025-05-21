module ufc.ptt.pttfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.web;

    opens ufc.ptt.pttfx to javafx.fxml;
    exports ufc.ptt.pttfx;
}