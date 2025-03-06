module mains.ssport {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires okhttp3;
    requires org.json;
    requires java.desktop;

    opens mains.ssport to javafx.fxml;
    exports mains.ssport;
    exports controllers;
    opens controllers to javafx.fxml;
}