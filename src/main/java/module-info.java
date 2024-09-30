module jjvu.jmc.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens jjvu.jmc.mazebank to javafx.fxml;
    exports jjvu.jmc.mazebank;
    exports jjvu.jmc.mazebank.controllers;
    exports jjvu.jmc.mazebank.controllers.admin;
    exports jjvu.jmc.mazebank.controllers.client;
    exports jjvu.jmc.mazebank.models;
    exports jjvu.jmc.mazebank.views;
}