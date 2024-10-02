package jjvu.jmc.mazebank.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.AccountType;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> accountSelector;
    public Label payeeAddressLabel;
    public TextField payeeAddressField;
    public TextField passwordField;
    public Button loginButton;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountSelector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        accountSelector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());

        accountSelector.valueProperty().addListener(observable ->
                Model.getInstance().getViewFactory().setLoginAccountType(accountSelector.getValue())
        );

        loginButton.setOnAction(actionEvent -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) errorLabel.getScene().getWindow(); // get the stage from a control

        Model.getInstance().getViewFactory().closeStage(stage);

        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT)
            Model.getInstance().getViewFactory().showClientWindow();
        else
            Model.getInstance().getViewFactory().showAdminWindow();
    }
}
