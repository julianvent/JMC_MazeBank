package jjvu.jmc.mazebank.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jjvu.jmc.mazebank.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox account_selector;
    public Label payee_address_label;
    public TextField payee_address_field;
    public TextField password_field;
    public Button login_button;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(actionEvent -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage)error_label.getScene().getWindow(); // get the stage from a control

        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}
