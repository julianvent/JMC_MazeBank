package jjvu.jmc.mazebank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jjvu.jmc.mazebank.views.ViewFactory;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory();

        viewFactory.showLoginWindow();
    }
}
