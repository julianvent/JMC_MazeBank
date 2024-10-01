package jjvu.jmc.mazebank;

import javafx.application.Application;
import javafx.stage.Stage;
import jjvu.jmc.mazebank.models.Model;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
