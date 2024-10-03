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
                setAccountSelector()
        );

        loginButton.setOnAction(actionEvent -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) errorLabel.getScene().getWindow(); // get the stage from a control


        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            // Evaluate Client Login Credentials
            Model.getInstance().evaluateClientCredentials(payeeAddressField.getText(), passwordField.getText());
            if (Model.getInstance().getClientLoginSuccessFlag()) {
                // Close the Login Stage
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showClientWindow();
            } else {
                payeeAddressField.setText("");
                passwordField.setText("");
                errorLabel.setText("No Such Login Credentials");
            }
        }
        else {
            // Evaluate Admin Login Credentials
            Model.getInstance().evaluateAdminCredentials(payeeAddressField.getText(), passwordField.getText());
            if (Model.getInstance().getAdminLoginSuccessFlag()) {
                // Close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showAdminWindow();
            } else {
                payeeAddressField.setText("");
                passwordField.setText("");
                errorLabel.setText("No Such Login Credentials");
            }
        }
    }

    private void setAccountSelector() {
        Model.getInstance().getViewFactory().setLoginAccountType(accountSelector.getValue());
        // Change Payee Address Label to Username
        if (accountSelector.getValue() == AccountType.ADMIN) {
            payeeAddressLabel.setText("Username: ");
        } else {
            payeeAddressLabel.setText("Payee Address: ");
        }
        // Clear error
        errorLabel.setText("");
    }
}
